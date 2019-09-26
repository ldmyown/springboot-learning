package com.telangel.springbootmybatis.service.impl;

import com.telangel.springbootmybatis.entity.FileProp;
import com.telangel.springbootmybatis.mapper.FileMapper;
import com.telangel.springbootmybatis.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @auther lid
 * @date   2019/3/30 11:14
 * @version 1.0.0
 */
@Service
public class FileServiceimpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public void addFile(FileProp file) {
        fileMapper.addFile(file);
    }
}
