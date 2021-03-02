package com.shireesha.casestudy.services;

import com.shireesha.casestudy.models.Gallery;
import com.shireesha.casestudy.models.Photo;
import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.repositories.GalleryRepository;
import com.shireesha.casestudy.repositories.PhotoRepository;
import com.shireesha.casestudy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService{

    @Autowired
    private PhotoRepository photoRepo;

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private UserRepository userRepo;

    @Override
    @Transactional
    public void saveImage(Long galleryId, MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user=userRepo.findByEmail(userDetails.getUsername());
            Gallery gallery = galleryRepository.findById(galleryId).get();
            List<Photo> galleryPhotos = gallery.getGalleryPhotos();
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }
            List<Photo> photos = user.getUserPhotos();
            Photo photo = new Photo();
            photo.setName(file.getOriginalFilename());
            System.out.println(photo.getName());
            photo.setTakenOn(new Date());
            photo.setImage(byteObjects);
            galleryPhotos.add(photo);
            photos.add(photo);
            user.setUserPhotos(photos);
            photo.setGalleries(new HashSet<>(Arrays.asList(gallery)));
            gallery.setGalleryPhotos(galleryPhotos);
            photoRepo.save(photo);
//            galleryRepository.save(gallery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveImage(MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user=userRepo.findByEmail(userDetails.getUsername());
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }
            List<Photo> photos = user.getUserPhotos();
            Photo photo = new Photo();
            photo.setName(file.getOriginalFilename());
            System.out.println(photo.getName());
            photo.setTakenOn(new Date());
            photo.setImage(byteObjects);
            photos.add(photo);
            user.setUserPhotos(photos);
            photoRepo.save(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
