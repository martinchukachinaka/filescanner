package cc.filescanner.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fi.solita.clamav.ClamAVClient;


@Service
public class ScannerService {

    @Value("${scanner.file.directory:/dockerdrive/malbox/}")
    private String scannerFileDirectory;

    @Value("${scanner.antimalware.host:localhost}")
    private String antiMalwareHost;

    @Value("${scanner.antimalware.port:3310}")
    private int antiMalwarePort;


    public boolean isCleanFile(String fileName) {
        ClamAVClient cl = new ClamAVClient(antiMalwareHost, antiMalwarePort);
        byte[] reply;
        try (InputStream input = new BufferedInputStream(new FileInputStream(String.format("%s/%s", scannerFileDirectory, fileName)))) {
            reply = cl.scan(input);
        } catch (Exception e) {
            throw new RuntimeException("Could not scan the input", e);
        }
        return ClamAVClient.isCleanReply(reply);
    }


    public boolean isCleanFile(MultipartFile file) {
        ClamAVClient cl = new ClamAVClient(antiMalwareHost, antiMalwarePort);
        byte[] reply;
        try (InputStream input = new BufferedInputStream(file.getInputStream())) {
            reply = cl.scan(input);
        } catch (Exception e) {
            throw new RuntimeException("Could not scan the input", e);
        }
        return ClamAVClient.isCleanReply(reply);
    }
}
