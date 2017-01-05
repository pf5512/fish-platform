package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by isunimp on 16/11/24.
 */
@Service("FileService")
public class FileServiceImpl {

    @Autowired
    FileDao fileDao;

    public String upload(InputStream inputStream, String fileName, String owner) throws Exception {
        return fileDao.save(inputStream, fileName, owner);
    }

    public InputStream file(String id) {
        return fileDao.file(id);
    }
}
