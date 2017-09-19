package com.example.fileupload.utils;

import com.example.fileupload.entity.MetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileUtil {
    public static final String UPLOAD_LOCATION = "upload_files/";

    public static MetaData convertToMetadata(MultipartFile multipartFile){
        MetaData metaData = new MetaData();
        metaData.setFileName(multipartFile.getOriginalFilename());
        metaData.setFilePath(new File(UPLOAD_LOCATION).getAbsolutePath() + "/" + multipartFile.getOriginalFilename());
        metaData.setFileSize(multipartFile.getSize());
        return metaData;
    }

    public static void saveUploadedFile(MultipartFile multipartFile, MetaData uploadFile) throws IOException{
        if(!new File(UPLOAD_LOCATION).exists()){
            new File(UPLOAD_LOCATION).mkdir();
        }
        multipartFile.transferTo(new File(uploadFile.getFilePath()));
    }
}
