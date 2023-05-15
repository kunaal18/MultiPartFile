package com.te.storefile.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.te.storefile.ImageCD;
import com.te.storefile.dao.FileData;
import com.te.storefile.dao.Storage;
import com.te.storefile.entity.FileSystemData;
import com.te.storefile.entity.ImageData;

@Service
public class StorageServices {
	@Autowired
	private Storage repository;
	@Autowired
	private FileData fileDataRepository;

	private final String Folder = "C:\\Users\\kunalnew\\Documents\\File_Data";

	public String uploadImage(MultipartFile file) throws IOException {

		ImageData imageData = repository.save(ImageData.builder().name(file.getOriginalFilename())
				.type(file.getContentType()).imageData(ImageCD.compressImage(file.getBytes())).build());
		if (imageData != null) {
			return "file uploaded successfully : " + file.getOriginalFilename();
		}
		return null;
	}

	public byte[] downloadImage(String fileName) {
		Optional<ImageData> dbImageData = repository.findByName(fileName);
		byte[] images = ImageCD.decompressImage(dbImageData.get().getImageData());
		return images;
	}

	public String uploadImageToFileSystem(MultipartFile file) throws IOException {
		String filePath = Folder + file.getOriginalFilename();
		FileSystemData saveFile = fileDataRepository.save(
				FileSystemData.builder().name(file.getName()).type(file.getContentType()).filePath(filePath).build());

		// the image which i will uplaod , i want to transfer to the filePath which i
		// specify here
		file.transferTo(new File(filePath));

		if (saveFile != null) {
			return "File Upload Successfully" + filePath;

		}
		return null;
	}

	public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
		Optional<FileSystemData> findByName = fileDataRepository.findByName(fileName);
		
		String filePath = findByName.get().getFilePath();
		// i want to conver it into byte array or I will use utility method from Files
		// class readAllBytes from this file path

		byte[] images = Files.readAllBytes(new File(filePath).toPath());

		return images;
	}

}
