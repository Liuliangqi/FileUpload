package com.example.fileupload.service;

import com.example.fileupload.entity.MetaData;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface UploadService {
    void init();
    void deleteAll();

    MetaData save(MultipartFile uploadFile);

    MetaData getFile(String fileName);

    Stream<Path> loadAll();
}
