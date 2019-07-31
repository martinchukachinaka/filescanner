package cc.filescanner.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileUtilService {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtilService.class);


    public String getMimeType(MultipartFile file) throws Exception {
        try {
            String mimeType = new Tika().detect(file.getBytes());
            LOG.info("mimeType: {}", mimeType);
            return mimeType;
        } catch (Exception e) {
            throw e;
        }
    }


    public String getFileExtension(MultipartFile file) throws Exception {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        LOG.info("extension for {} = {}", file.getOriginalFilename(), extension);
        return extension;
    }
}
