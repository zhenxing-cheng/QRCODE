package com.trigon.qrcode.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeApi {
	

	@SuppressWarnings("deprecation")
	public String encodeQRCode(String path, String text) {
		int width = 300;
		int height = 300;
		// 二维码的图片格式
		String format = "jpg";
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix;
		File filePath = new File(path);
		if(!filePath.exists()) {
			filePath.mkdir();
		}
		File outputFile = new File(path + "new.jpg");
		try {
			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			// 生成二维码
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputFile.getPath();
	}

	public String decodeQRCode(String Path) {
		MultiFormatReader formatReader = new MultiFormatReader();
		File file = new File(Path);
		Result result = null;
		try {
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			result = formatReader.decode(binaryBitmap, hints);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.getText();
	}

}
