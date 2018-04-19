package com.dhxx.web.transport.car;

import org.springframework.web.multipart.MultipartFile;

public class CarFile {
	
	private MultipartFile carPhotoFile;//车辆照片file
	private MultipartFile carPhoto1File;//车辆照片1file
	private MultipartFile carPhoto2File;//车辆照片2file
	private MultipartFile carPhoto3File;//车辆照片3file
	private MultipartFile carPhoto4File;//车辆内景照片4file
	private MultipartFile carPhoto5File;//车辆内景照片5file
    private MultipartFile drivingLicenseProsFile;//行驶证正面file
    private MultipartFile drivingLicenseConsFile;//行驶证反面file
    private MultipartFile operationLicenseFile;//运营许可证file
    private MultipartFile xianlupaiProsFile;//线路牌正面file
    private MultipartFile xianlupaiConsFile;//线路牌反面file
    private MultipartFile policyFile;//保险单file

	public MultipartFile getCarPhoto4File() {
		return carPhoto4File;
	}

	public void setCarPhoto4File(MultipartFile carPhoto4File) {
		this.carPhoto4File = carPhoto4File;
	}

	public MultipartFile getCarPhoto5File() {
		return carPhoto5File;
	}

	public void setCarPhoto5File(MultipartFile carPhoto5File) {
		this.carPhoto5File = carPhoto5File;
	}

	public MultipartFile getCarPhotoFile() {
		return carPhotoFile;
	}
	public void setCarPhotoFile(MultipartFile carPhotoFile) {
		this.carPhotoFile = carPhotoFile;
	}
	public MultipartFile getCarPhoto1File() {
		return carPhoto1File;
	}
	public void setCarPhoto1File(MultipartFile carPhoto1File) {
		this.carPhoto1File = carPhoto1File;
	}
	public MultipartFile getCarPhoto2File() {
		return carPhoto2File;
	}
	public void setCarPhoto2File(MultipartFile carPhoto2File) {
		this.carPhoto2File = carPhoto2File;
	}
	public MultipartFile getCarPhoto3File() {
		return carPhoto3File;
	}
	public void setCarPhoto3File(MultipartFile carPhoto3File) {
		this.carPhoto3File = carPhoto3File;
	}
	public MultipartFile getDrivingLicenseProsFile() {
		return drivingLicenseProsFile;
	}
	public void setDrivingLicenseProsFile(MultipartFile drivingLicenseProsFile) {
		this.drivingLicenseProsFile = drivingLicenseProsFile;
	}
	public MultipartFile getDrivingLicenseConsFile() {
		return drivingLicenseConsFile;
	}
	public void setDrivingLicenseConsFile(MultipartFile drivingLicenseConsFile) {
		this.drivingLicenseConsFile = drivingLicenseConsFile;
	}
	public MultipartFile getOperationLicenseFile() {
		return operationLicenseFile;
	}
	public void setOperationLicenseFile(MultipartFile operationLicenseFile) {
		this.operationLicenseFile = operationLicenseFile;
	}
	
	public MultipartFile getXianlupaiProsFile() {
		return xianlupaiProsFile;
	}
	public void setXianlupaiProsFile(MultipartFile xianlupaiProsFile) {
		this.xianlupaiProsFile = xianlupaiProsFile;
	}
	public MultipartFile getXianlupaiConsFile() {
		return xianlupaiConsFile;
	}
	public void setXianlupaiConsFile(MultipartFile xianlupaiConsFile) {
		this.xianlupaiConsFile = xianlupaiConsFile;
	}
	public MultipartFile getPolicyFile() {
		return policyFile;
	}
	public void setPolicyFile(MultipartFile policyFile) {
		this.policyFile = policyFile;
	}
    
}
