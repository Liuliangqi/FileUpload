package com.example.fileupload.controller;

import com.example.fileupload.entity.MetaData;
import com.example.fileupload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class FileUploadController {

    @Autowired
    private final UploadService uploadService;

    @Autowired
    public FileUploadController(UploadService uploadService){
        this.uploadService = uploadService;
    }

    // show upload page and list existing files
    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        // display all uploaded files' name
        model.addAttribute("files", uploadService.loadAll().map(path -> path.getFileName().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    // single file upload
    @PostMapping("/api/upload")
    public String uploadFile(@RequestParam("file")MultipartFile uploadFile,
                                        RedirectAttributes redirectAttributes){
        if(uploadFile.isEmpty()){
            redirectAttributes.addFlashAttribute("message", "Please Choose a file first!");
        }else{
            uploadService.save(uploadFile);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + uploadFile.getOriginalFilename() + "!");
        }
        return "redirect:/";
    }


    @GetMapping("/api/get" )
    public String getFileDetails(@RequestParam("fileName") String fileName, RedirectAttributes redirectAttributes) throws FileNotFoundException{
        if(uploadService.getFile(fileName) == null)
            redirectAttributes.addFlashAttribute("fileInformation", "This file does not exist");
        else{
            MetaData file = uploadService.getFile(fileName);
            String[] fileInfos = {
                    "fileID: " +file.getFileID().toString(),
                    "fileName: " + file.getFileName(),
                    "filePath: " + file.getFilePath(),
                    "fileSize: " + file.getFileSize().toString() + "KB"
            };
            List<String> fileInfo = new ArrayList<>(Arrays.asList(fileInfos));
            redirectAttributes.addFlashAttribute("fileInformation",fileInfo);
        }
        return "redirect:/";
    }
}
