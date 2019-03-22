package com.telangel.param;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author： lid
 * @date： 2019/3/21 17:29
 */
public class MultipartFileParam {
    /**
     * 总分片数量
     */
    private int chunks;

    /**
     * 当前为第几块分片
     */
    private int chunk;

    /**
     * 当前分片大小
     */
    private long size = 0L;

    /**
     * 文件名
     */
    private String name;

    /**
     * 分片对象
     */
    private MultipartFile file;

    /**
     * MD5
     */
    private String md5;

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
