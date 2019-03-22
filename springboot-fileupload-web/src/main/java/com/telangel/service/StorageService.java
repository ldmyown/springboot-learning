package com.telangel.service;

import com.telangel.param.MultipartFileParam;

import java.io.IOException;

/**
 * @author： lid
 * @date： 2019/3/21 16:51
 */
public interface StorageService {

    /**
     * 初始化方法
     */
    void init();

    void deleteAll();

    /**
     * 文件上传方法一
     * @param param
     * @throws IOException
     */
    void uploadFileRandomAccessFile(MultipartFileParam param) throws IOException;

    /**
     * 上传文件方法2
     * 处理文件分块，基于MappedByteBuffer来实现文件的保存
     *
     * @param param
     * @throws IOException
     */
    void uploadFileByMappedByteBuffer(MultipartFileParam param) throws IOException;

}
