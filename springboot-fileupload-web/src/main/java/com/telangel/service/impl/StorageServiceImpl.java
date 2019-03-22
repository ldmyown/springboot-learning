package com.telangel.service.impl;

import com.telangel.constant.Constants;
import com.telangel.param.MultipartFileParam;
import com.telangel.service.StorageService;
import com.telangel.util.DateUtils;
import com.telangel.util.FileMD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * 文件处理类
 * @author： lid
 * @date： 2019/3/21 16:51
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    /**
     * 文件上传的根目录
     */
    @Value("${breakpoint.upload.dir}")
    private String rootPath;

    private String confRootPath;

    /**
     * 单个分块的大小，此值必须与前端保持一致
     */
    @Value("${breakpoint.upload.chunkSize}")
    private Long chunkSize;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void init() {
        File dir = new File(rootPath);
        if (dir.exists()) {
            log.info("文件根目录已经存在不需要创建，根目录为 {}", dir.getAbsoluteFile());
        } else {
            dir.mkdirs();
            log.info("文件根目录不存在，创建成功，根目录为 {}", dir.getAbsoluteFile());
        }
        confRootPath = rootPath + File.separator + "conf";

        File confDir = new File(confRootPath);
        if (confDir.exists()) {
            log.info("文件进度目录已经存在不需要创建，根目录为 {}", confDir.getAbsoluteFile());
        } else {
            confDir.mkdirs();
            log.info("文件进度目录不存在，创建成功，根目录为 {}", confDir.getAbsoluteFile());
        }
    }

    @Override
    public void deleteAll() {
        log.info("开发初始化清理数据，start");
        FileSystemUtils.deleteRecursively(new File(rootPath));
        stringRedisTemplate.delete(Constants.FILE_UPLOAD_STATUS);
        stringRedisTemplate.delete(Constants.FILE_MD5_KEY);
        log.info("开发初始化清理数据，end");
    }

    @Override
    public void uploadFileRandomAccessFile(MultipartFileParam param) throws IOException {
        String fileName = param.getName();
        String dirPath = rootPath + param.getMd5();
        String tmpFileName = fileName + "_tmp";
        File dir = new File(dirPath);
        File tmpFile = new File(dir, tmpFileName);
        if (!dir.exists()) {
            tmpFile.mkdirs();
        }

        RandomAccessFile accessFile = new RandomAccessFile(tmpFile, "rw");
        long offset = chunkSize * param.getChunk();
        // 定义分片的偏移量
        accessFile.seek(offset);
        // 将分片数据写入到文件中
        accessFile.write(param.getFile().getBytes());
        // 释放流
        accessFile.close();

        // 检测是否已经传输完成，并且设置传输进度
        Boolean isOk = checkAndSetProgress(param);
        if(isOk) {
            renameFile(tmpFile, fileName);
            log.info("文件{}上传完成", fileName);
        }

    }

    @Override
    public void uploadFileByMappedByteBuffer(MultipartFileParam param) throws IOException {
        FileChannel fileChannel = null;

        String fileName;
        File tmpFile;
        try {
            fileName = param.getName();
            String tmpFileName = fileName + "_tmp";
            File dir = new File(rootPath, DateUtils.format(new Date()));
            tmpFile = new File(dir, tmpFileName);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            long offset = chunkSize * param.getChunk();
            RandomAccessFile accessFile = new RandomAccessFile(tmpFile, "rw");
            fileChannel = accessFile.getChannel();
            byte[] fileData = param.getFile().getBytes();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
            mappedByteBuffer.put(fileData);
            // 释放
            FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
            fileChannel.close();
        } finally {
            if (fileChannel != null && fileChannel.isOpen()) {
                fileChannel.close();
            }
        }
        // 检测是否已经传输完成，并且设置传输进度
        Boolean isOk = checkAndSetProgress(param);
        if(isOk) {
            boolean result = renameFile(tmpFile, fileName);
            log.info("重命名结果：{}", Boolean.valueOf(result));
            log.info("文件{}上传完成", fileName);
        }

    }

    /**
     * 检测文件是否传输完成，并且设置传输的进度
     * @param param
     */
    private boolean checkAndSetProgress(MultipartFileParam param) throws IOException {
        String fileName = param.getName();
        File confMd5Dir = new File(confRootPath, param.getMd5());
        if (!confMd5Dir.exists()) {
            confMd5Dir.mkdirs();
        }

        // 创建一个进度标志文件
        File confFile = new File(confMd5Dir, fileName + "_conf");
        RandomAccessFile accessFile = new RandomAccessFile(confFile, "rw");
        accessFile.setLength(param.getChunks());
        // 将进度写入到文件中
        accessFile.seek(param.getChunk());
        accessFile.write(Byte.MAX_VALUE);
        accessFile.close();
        // 判断当前文件是否已经完成，如果所有位置都已经写入值，则表示上传完成
        byte[] bytes = FileUtils.readFileToByteArray(confFile);
        boolean isComplete = true;

        for (byte aByte : bytes) {
            if (Byte.MAX_VALUE != aByte) {
                isComplete = false;
                break;
            }
        }
        if (isComplete) {
            // 已经完成， 则更新文件上传状态和文件的路径
            stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "true");
            return true;
        } else {
            if (!stringRedisTemplate.opsForHash().hasKey(Constants.FILE_UPLOAD_STATUS, param.getMd5())) {
                stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "false");
            }
            if (stringRedisTemplate.hasKey(Constants.FILE_MD5_KEY + param.getMd5())) {
                stringRedisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + param.getMd5(), confFile.getAbsolutePath());
            }
            return false;
        }
    }

    /**
     * 文件重命名
     *
     * @param toBeRenamed   将要修改名字的文件
     * @param toFileNewName 新的名字
     * @return
     */
    public boolean renameFile(File toBeRenamed, String toFileNewName) {
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            log.info("File does not exist: " + toBeRenamed.getName());
            return false;
        }
        String p = toBeRenamed.getParent();
        File newFile = new File(p + File.separatorChar + toFileNewName);
        //修改文件名
        return toBeRenamed.renameTo(newFile);
    }

}
