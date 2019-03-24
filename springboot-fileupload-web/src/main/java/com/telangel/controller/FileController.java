package com.telangel.controller;

import com.telangel.constant.Constants;
import com.telangel.param.MultipartFileParam;
import com.telangel.service.StorageService;
import com.telangel.util.CacheUtils;
import com.telangel.vo.Result;
import com.telangel.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author： lid
 * @date： 2019/3/21 16:48
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    StorageService storageService;

    /**
     * 校验md5值，并且返回文件上传进度
     * @param md5
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/checkFileMd5", method = RequestMethod.POST)
    public ResultVo checkFileMd5(String md5) throws IOException {
        Object o = CacheUtils.cache.hget(Constants.FILE_UPLOAD_STATUS, md5);
        // 文件没有上传
        if (o == null) {
            return new ResultVo(Result.NO_HAVE);
        }
         // 文件已经上传
        boolean fileStatus = Boolean.parseBoolean(o.toString());
        if(fileStatus) {
            return new ResultVo(Result.IS_HAVE);
        }
        // 文件上传了一部分
        else {
            String value = (String)CacheUtils.cache.get(Constants.FILE_MD5_KEY + md5);
            // 在redis中没有存储进度文件的地址，则重新上传
            if (StringUtils.isEmpty(value)) {
                return new ResultVo(Result.NO_HAVE);
            }
            File confFile = new File(value);
            if(!confFile.exists()) {
                return new ResultVo(Result.NO_HAVE);
            }
            byte[] bytes = FileUtils.readFileToByteArray(confFile);
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < bytes.length; i++) {
                if (Byte.MAX_VALUE != bytes[i]) {
                    missChunkList.add(i + "");
                }
            }
            return new ResultVo(Result.ING_HAVE, missChunkList);
        }
    }

    /**
     * 文件上传
     * @param param
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public ResponseEntity fileUpload(MultipartFileParam param, HttpServletRequest request) throws IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        int chunk = param.getChunk();
        if (isMultipart) {
            log.info("上传文件分块：{} 开始", chunk);
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                storageService.uploadFileByMappedByteBuffer(param);
            } catch (IOException e) {
                log.error("分块文件上传失败，失败的分块为 {}", chunk, e);
                return ResponseEntity.notFound().build();
            }
            log.info("上传文件分块：{} 结束", chunk);
        }
        return ResponseEntity.ok().body("上传成功");
    }



}
