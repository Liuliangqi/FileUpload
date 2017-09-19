package com.example.fileupload.service;

import com.example.fileupload.dao.UploadDAO;
import com.example.fileupload.entity.MetaData;
import com.example.fileupload.properties.FileUploadProperty;
import com.example.fileupload.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Service
public class UploadServiceImpl implements UploadService {

    private final Path rootLocation = Paths.get(FileUploadProperty.location);

    @Autowired
    private UploadDAO uploadDAO;

//    @Autowired
//    public UploadServiceImpl(FileUploadProperty fileUpload){
//        this.rootLocation = Paths.get(fileUpload.getLocation());
//    }

    @Override
    public MetaData save(MultipartFile uploadFile) {
        MetaData file = FileUtil.convertToMetadata(uploadFile);
        MetaData result = uploadDAO.save(file);
        try {
            FileUtil.saveUploadedFile(uploadFile, file);
        } catch (IOException e) {
            throw new RuntimeException("File save failed", e);
        }
        return result;
    }

    @Override
    public MetaData getFile(String fileName) {

        return uploadDAO.findByFileName(fileName);
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            Stream<Path> paths =  Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
            return paths;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
}
