package com.example.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 处理文件
 */
public interface FileService {

    /**
     * 存储头像以Base64形式到数据库
     * @param file 头像文件
     * @return 存储了几个
     */
    int saveIcon(String  file);
}
