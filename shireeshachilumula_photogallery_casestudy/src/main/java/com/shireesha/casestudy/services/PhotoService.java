package com.shireesha.casestudy.services;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    void saveImage(Long galleryId, MultipartFile file);
    void saveImage(MultipartFile file);
}
