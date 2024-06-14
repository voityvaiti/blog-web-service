package com.myproject.blogwebservice.service.abstraction;

import com.myproject.blogwebservice.pojo.ImagePojo;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    ImagePojo uploadFile(MultipartFile file, String folderName);

    void deleteFile(String publicId, String folderName);

}
