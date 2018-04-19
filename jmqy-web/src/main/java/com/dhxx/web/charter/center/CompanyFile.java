package com.dhxx.web.charter.center;

import org.springframework.web.multipart.MultipartFile;

public class CompanyFile {
	
	private MultipartFile businessPicFile;//企业营业执照附件

    private MultipartFile file;

	public MultipartFile getBusinessPicFile() {
		return businessPicFile;
	}

	public void setBusinessPicFile(MultipartFile businessPicFile) {
		this.businessPicFile = businessPicFile;
	}

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
