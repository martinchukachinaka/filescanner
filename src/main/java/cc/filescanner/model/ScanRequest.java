package cc.filescanner.model;

import org.springframework.web.multipart.MultipartFile;


public class ScanRequest {

    private String fileName;

    private String ownerId;

    private MultipartFile file;


    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getOwnerId() {
        return ownerId;
    }


    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    public MultipartFile getFile() {
        return file;
    }


    public void setFile(MultipartFile file) {
        this.file = file;
    }


    @Override
    public String toString() {
        return String.format("owner: %s, file: %s", ownerId, file);
    }
}
