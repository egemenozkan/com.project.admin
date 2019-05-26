package com.project.client.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.api.data.model.file.LandingPageFile;
import com.project.api.data.model.file.MyFile;
import com.project.api.data.model.file.UploadFileResponse;

public interface IFileService {
	List<UploadFileResponse> saveFiles(MultipartFile[] files, long userId, int pageType, long pageId);
	List<MyFile> getFilesByPageId(int pageType, long pageId);
	List<LandingPageFile> getLandingPageFiles();
}
