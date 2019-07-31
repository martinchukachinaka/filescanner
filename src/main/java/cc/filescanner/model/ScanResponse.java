package cc.filescanner.model;

public class ScanResponse {

    private Boolean valid = false;

    private String code;

    private String message;

    private Boolean cleanFile = false;

    private Boolean validUploadLimit = false;

    private Boolean validFileType = false;


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public Boolean getValid() {
        return valid;
    }


    public void setValid(Boolean valid) {
        this.valid = valid;
    }


    public Boolean getCleanFile() {
        return cleanFile;
    }


    public void setCleanFile(Boolean cleanFile) {
        this.cleanFile = cleanFile;
    }


    public Boolean getValidUploadLimit() {
        return validUploadLimit;
    }


    public void setValidUploadLimit(Boolean validUploadLimit) {
        this.validUploadLimit = validUploadLimit;
    }


    public Boolean getValidFileType() {
        return validFileType;
    }


    public void setValidFileType(Boolean validFileType) {
        this.validFileType = validFileType;
    }


    public void updateAllValid() {
        valid = cleanFile && validFileType && validUploadLimit;
        setCode(valid ? "00" : "01");
    }
}
