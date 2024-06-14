package com.myproject.blogwebservice.service.impl;

import com.cloudinary.Cloudinary;
import com.myproject.blogwebservice.exception.CloudException;
import com.myproject.blogwebservice.pojo.ImagePojo;
import com.myproject.blogwebservice.service.abstraction.CloudinaryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    @Override
    public ImagePojo uploadFile(MultipartFile file, String folderName) {

        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);

            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);


            ImagePojo imagePojo = new ImagePojo();

            String publicId = (String) uploadedFile.get("public_id");
            imagePojo.setPublicId(publicId);

            imagePojo.setUrl(cloudinary.url().secure(true).generate(publicId));

            return imagePojo;

        } catch (Exception e) {
            throw new CloudException("Error occurred when uploading image.");
        }
    }

    @Override
    public void deleteFile(String publicId, String folderName) {

        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);

            cloudinary.uploader().destroy(publicId, options);

        } catch (Exception e) {
            throw new CloudException("Error occurred when removing image.");
        }
    }
}
