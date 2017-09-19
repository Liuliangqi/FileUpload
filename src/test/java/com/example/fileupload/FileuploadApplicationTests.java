package com.example.fileupload;

import com.example.fileupload.dao.UploadDAO;
import com.example.fileupload.entity.MetaData;
import com.example.fileupload.properties.FileUploadProperty;
import com.example.fileupload.service.UploadService;
import com.example.fileupload.service.UploadServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileuploadApplicationTests {

	@Mock
	UploadDAO uploadDAO;


	@InjectMocks
	UploadServiceImpl uploadServiceImpl;


	private MetaData file = new MetaData();
	private Integer fileId = 1;
	private String fileName = "abc.txt";
	private String filePath = "upload_file";
	private Long fileSize = 1000000L;

	@Before
	public void init(){
		file.setFileID(fileId);
		file.setFileName(fileName);
		file.setFilePath(filePath);
		file.setFileSize(fileSize);
	}

	@Test
	public void testGetFileDetails() {
		when(uploadDAO.findByFileName(fileName)).thenReturn(file);
		assertEquals(uploadServiceImpl.getFile(fileName).toString(), file.toString());
	}


	@Test(expected = RuntimeException.class)
	public void testUploadService() throws RuntimeException{
		MockMultipartFile mockMultipartFile = new MockMultipartFile("newFile", new byte[0]);
		uploadServiceImpl.save(mockMultipartFile);
	}
}
