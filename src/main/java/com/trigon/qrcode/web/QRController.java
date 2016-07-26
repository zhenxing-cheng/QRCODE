package com.trigon.qrcode.web;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trigon.qrcode.service.QRCodeService;
@Controller
@Service
@RequestMapping("/enco")

public class QRController {
	@Value("${qrcode.folder}")
	private String qrcodeFolder;
	
	
	@Autowired
	QRCodeService service;
	
	@RequestMapping( method = RequestMethod.POST, produces = "text/html")
	@ResponseBody
	public String getQRCodePicture(
			HttpSession session,
			@RequestParam(value="inputText") String inputText,
			Model uiModel) {
		String picUrl = service.encodeQRCode(inputText);
		return picUrl;
	}
	
	@RequestMapping(value = "/get/{name}", params="random", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(HttpServletResponse response, @PathVariable String name) throws IOException {
		return processImage(response, name);
	}

	private ResponseEntity<byte[]> processImage(HttpServletResponse response, String name) throws IOException {

		File file = new File(qrcodeFolder + name +".jpg");
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		byte[] result = null;
		InputStream in = new FileInputStream(file);
		result = IOUtils.toByteArray(in);
		in.close();
		headers.setContentLength(file.length());

		return new ResponseEntity<byte[]>(result, headers, HttpStatus.OK);
	}

}
