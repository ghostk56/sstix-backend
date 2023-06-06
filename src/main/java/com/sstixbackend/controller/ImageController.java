package com.sstixbackend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sstixbackend.response.RestfulResponse;
import com.sstixbackend.service.ImageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@GetMapping("/{imageName}")
	public ResponseEntity<byte[]> select(@PathVariable String imageName) throws IOException {
		if (imageName == null)
			return ResponseEntity.notFound().build();

		byte[] imageData = imageService.getImage(imageName);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return new ResponseEntity<byte[]>(imageData, headers, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<RestfulResponse<?>> insert(MultipartFile image) throws IOException {
		if (image != null) {
			RestfulResponse<String> response = new RestfulResponse<String>("00000", "成功",
					imageService.saveImage(image));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return null;
	}
}
