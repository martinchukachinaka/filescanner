package cc.filescanner.redis;

import java.util.Date;

import org.springframework.data.redis.core.RedisHash;


@RedisHash("UploadRate")
public class UploadRate {

    private Date creationDate = new Date();

    private Date lastModifiedDate = new Date();

    private String id;

    private String fileName;

    private int uploadCount;


    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public int getUploadCount() {
        return uploadCount;
    }


    public void setUploadCount(int uploadCount) {
        this.uploadCount = uploadCount;
    }


    /**
     * gets the logged in user id
     *
     * @return
     */
    public String getId() {
        return id;
    }


    /**
     * sets the logged in user id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return String.format("id: %s, file: %s, count: %d, date: %s", id, fileName, uploadCount, lastModifiedDate);
    }


    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }


    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    public Date getCreationDate() {
        return creationDate;
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Long getElapsedTime() {
        return lastModifiedDate.getTime() - creationDate.getTime();
    }
}
