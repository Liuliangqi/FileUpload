package com.example.fileupload;


import com.example.fileupload.properties.FileUploadProperty;
import com.example.fileupload.service.UploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableConfigurationProperties(FileUploadProperty.class)
public class FileuploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileuploadApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UploadService uploadService) {
		return (args) -> {
			uploadService.deleteAll();
			uploadService.init();
		};
	}
}
