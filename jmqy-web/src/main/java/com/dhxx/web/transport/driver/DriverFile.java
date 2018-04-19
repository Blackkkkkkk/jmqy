package com.dhxx.web.transport.driver;

import org.springframework.web.multipart.MultipartFile;

public class DriverFile {
	
	private MultipartFile headshotFile;//头像file
    private MultipartFile idCardProsFile;//身份证正面file
    private MultipartFile idCardConsFile;//身份证反面file
    private MultipartFile drivingLicenseFile;//驾驶证file
    private MultipartFile workLicenseFile;//上岗证file
    
	public MultipartFile getHeadshotFile() {
		return headshotFile;
	}
	public void setHeadshotFile(MultipartFile headshotFile) {
		this.headshotFile = headshotFile;
	}
	public MultipartFile getIdCardProsFile() {
		return idCardProsFile;
	}
	public void setIdCardProsFile(MultipartFile idCardProsFile) {
		this.idCardProsFile = idCardProsFile;
	}
	public MultipartFile getIdCardConsFile() {
		return idCardConsFile;
	}
	public void setIdCardConsFile(MultipartFile idCardConsFile) {
		this.idCardConsFile = idCardConsFile;
	}
	public MultipartFile getDrivingLicenseFile() {
		return drivingLicenseFile;
	}
	public void setDrivingLicenseFile(MultipartFile drivingLicenseFile) {
		this.drivingLicenseFile = drivingLicenseFile;
	}
	public MultipartFile getWorkLicenseFile() {
		return workLicenseFile;
	}
	public void setWorkLicenseFile(MultipartFile workLicenseFile) {
		this.workLicenseFile = workLicenseFile;
	}
    
}
