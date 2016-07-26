package com.trigon.qrcode.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trigon.qrcode.api.QRCodeApi;

@Service

public class QRCodeService {
	@Value("${qrcode.folder}")
	private String qrcodeFolder;
	
	public String encodeQRCode(String text) { 
		QRCodeApi api = new QRCodeApi();
		String pictureUrl = api.encodeQRCode(qrcodeFolder, text);
		return pictureUrl;
	}

}
