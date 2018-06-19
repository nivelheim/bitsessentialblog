package com.bitsessential.blog.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.bitsessential.blog.services.AmazonS3Service;
import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/storage")
public class StorageController {
	private AmazonS3Service amazonService;
	
	public StorageController(AmazonS3Service service) {
		this.amazonService = service;
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonService.uploadFile(file);
    }

	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public String deleteFile(@RequestParam("imageurl") String fileUrl) {
        this.amazonService.deleteFileFromS3Bucket(fileUrl);
        return "redirect:/posts";
    }
	
	public void initBucket() {
	
	}

}
