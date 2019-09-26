package com.telangel.springbootmybatis.entity;
/**
 *
 * @auther lid
 * @date   2019/3/30 11:12
 * @version 1.0.0
 */
public class FileProp {
    private Integer id;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private Integer fileStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }
}
