package com.shireesha.casestudy.services;

import org.springframework.web.multipart.MultipartFile;
//interface for photoservices
public interface PhotoService {
    void saveImage(Long galleryId, MultipartFile file);
    void saveImage(MultipartFile file);
}
