package com.sstixbackend.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	@Value("${product.image.directory}")
	private String imageDirectory;

	public byte[] getImage(String imageName) throws IOException {
		byte[] imageBytes = null;

		File imageFile = new File(imageDirectory + imageName);
		BufferedImage image = ImageIO.read(imageFile);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpeg", baos);
		imageBytes = baos.toByteArray();

		return imageBytes;
	}

	public String saveImage(MultipartFile image) throws IOException {
		String originaName = image.getOriginalFilename();
		String suffix = originaName.substring(originaName.lastIndexOf("."));
		String imageName = UUID.randomUUID().toString() + suffix;

		File dir = new File(imageDirectory);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		image.transferTo(new File(dir, imageName));

		return imageName;
	}
}
