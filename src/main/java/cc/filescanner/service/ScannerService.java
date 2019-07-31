package cc.filescanner.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cc.filescanner.model.ScanRequest;
import cc.filescanner.model.ScanResponse;
import fi.solita.clamav.ClamAVClient;


@Service
public class ScannerService {

    @Value("${scanner.file.directory:/dockerdrive/malbox/}")
    private String scannerFileDirectory;

    @Value("${scanner.antimalware.host:localhost}")
    private String antiMalwareHost;

    @Value("${scanner.antimalware.port:3310}")
    private int antiMalwarePort;

    @Value("#{'${scanner.whitelist.extension}'.split(',')}")
    private List<String> extensionsWhiteList;

    @Value("#{'${scanner.whitelist.mime.type}'.split(',')}")
    private List<String> mimeTypesWhiteList;

    @Autowired
    private FileUtilService fileUtilService;

    @Autowired
    private UploadLimitService uploadLimitService;


    public boolean isCleanFile(String fileName) {
        return doCleanFileCheck(fileName, (aFileName) -> {
            try {
                return new BufferedInputStream(new FileInputStream(String.format("%s/%s", scannerFileDirectory, aFileName)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }


    public boolean isCleanFile(MultipartFile file) {
        return doCleanFileCheck(file, (multiPartFile) -> {
            try {
                return new BufferedInputStream(multiPartFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not scan the input", e);
            }
        });
    }


    private <T> boolean doCleanFileCheck(T param, Function<T, InputStream> supplier) {
        ClamAVClient cl = new ClamAVClient(antiMalwareHost, antiMalwarePort);
        byte[] reply;
        try (InputStream input = supplier.apply(param)) {
            reply = cl.scan(input);
        } catch (Exception e) {
            throw new RuntimeException("Could not scan the input", e);
        }
        return ClamAVClient.isCleanReply(reply);
    }


    public ScanResponse scan(ScanRequest request) {
        ScanResponse response = new ScanResponse();
        response.setValidFileType(isAllowedFileType(request.getFile()));
        if (response.getValidFileType()) {
            response.setValidUploadLimit(uploadLimitService.checkUploadLimit(request));
        }
        if (response.getValidUploadLimit()) {
            response.setCleanFile(isCleanFile(request.getFile()));
        }
        response.updateAllValid();
        return response;
    }


    private boolean isAllowedFileType(MultipartFile file) {
        try {
            return extensionsWhiteList.contains(fileUtilService.getFileExtension(file)) && mimeTypesWhiteList.contains(fileUtilService.getMimeType(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
