package com.project.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.api.data.model.file.LandingPageFile;
import com.project.api.data.model.file.UploadFileResponse;
import com.project.client.service.IFileService;



@Controller
public class MultimediaController {
	
	@Autowired
	private IFileService fileService;
	
	
	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") long userId,
			@RequestParam("pageType") int pageType, @RequestParam("pageId") long pageId) {
		String fileName = null; //fileStorageService.storeFile(file, userId, pageType, pageId);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public @ResponseBody List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("userId") long userId,
			@RequestParam("pageType") int pageType, @RequestParam("pageId") long pageId) {
		
		return fileService.saveFiles(files, userId, pageType, pageId);
			
		
	//	return Arrays.asList(files).stream().map(file -> uploadFile(file, userId, pageType, pageId)).collect(Collectors.toList());
	}
	
	@GetMapping("/multimedia/list")
	public String getMultimediaList(Model model) {
		/* Places - Files */
		List<LandingPageFile> files = fileService.getLandingPageFiles();
		model.addAttribute("files", files);
		return "multimedia/list";
	}
	
	
}
