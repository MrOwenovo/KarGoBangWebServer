package com.example.service.impl;

import com.example.controller.exception.MyFileException;
import com.example.dao.UserMapper;
import com.example.entity.constant.ThreadDetails;
import com.example.entity.repo.BASE64DecodeMultipartFile;
import com.example.service.FileService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    UserMapper userMapper;

    @SneakyThrows
    @Override
    public int saveIcon(String file) {
        String username = ThreadDetails.getUsername();

        return userMapper.modifyIcon(username, file);
    }
}
