package com.tsp.service;

import com.tsp.bean.File;
import com.tsp.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository repository;

    public List<File> findAll() {

        List<File> file = (List<File>) repository.findAll();
        return file;
    }
    public File findOne(String file_name) {

        return repository.findOne(file_name);
    }
    public void deleteOne(String file_name) {

        repository.delete(file_name);
    }
    public void updateOne(File file) {
        //TODO
    }

    public File uploadFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());


        File dbFile = null;
        try {
            dbFile = new File(fileName, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return repository.save(dbFile);

    }

    @Transactional
    public boolean insertOne(File file) {
        File lc = repository.findOne(file.getFile_name());
        if(lc == null) {
            return true;
        }
        return false;
    }
}
