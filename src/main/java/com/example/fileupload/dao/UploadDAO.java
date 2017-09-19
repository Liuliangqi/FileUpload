package com.example.fileupload.dao;

import com.example.fileupload.entity.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadDAO extends JpaRepository<MetaData, Integer> {
    MetaData findByFileName(String fileName);
}
