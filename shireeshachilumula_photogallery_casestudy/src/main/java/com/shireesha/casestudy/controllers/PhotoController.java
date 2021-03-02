package com.shireesha.casestudy.controllers;

import com.shireesha.casestudy.exceptions.GalleryResourceException;
import com.shireesha.casestudy.exceptions.UnHandledExceptions;
import com.shireesha.casestudy.models.Gallery;
import com.shireesha.casestudy.models.Photo;
import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.repositories.PhotoRepository;
import com.shireesha.casestudy.repositories.UserRepository;
import com.shireesha.casestudy.services.CustomUserDetails;
import com.shireesha.casestudy.services.PhotoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class PhotoController {
    @Autowired
    private PhotoRepository photoRepo;

    @Autowired
    private UserRepository userRepo;

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }


    @GetMapping("/photos")
    public String getAllPhotos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<Photo> listPhotos = userRepo.findByEmail(userDetails.getUsername()).getUserPhotos();
        model.addAttribute("photos", listPhotos);
        return "user_photos";
    }

    @GetMapping("/photos/today")
    public String todayPhotos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Date startdate = new Date();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date endDate = Date.from(LocalDate.now().minusDays(1).atStartOfDay(defaultZoneId).toInstant());
        List<Photo> photos = photoRepo.findAllByTakenOnBetweenAndUsersIn(endDate, startdate, Arrays.asList(userRepo.findByEmail(userDetails.getUsername())));
        model.addAttribute("photos", photos);
        return "user_photos";
    }


    @PostMapping("/photo/search")
    public String searchImages(@RequestParam("imageName") String name, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            List<Photo> photos = photoRepo.findByNameContainingAndUsersIn(name, Arrays.asList(userRepo.findByEmail(userDetails.getUsername())));
            model.addAttribute("photos", photos);
            return "user_photos";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Gallery with name: " + name +  " Not Found", exc);
        }
    }

    //@GetMapping("/photos/{}")

    @GetMapping("/image/{imageId}")
    public void renderImageFromDB(@PathVariable String imageId, HttpServletResponse response) throws IOException {
        try {
            Photo photo = photoRepo.findById(Long.valueOf(imageId)).get();
            //System.out.println("Found image" + photo.getName());
            byte[] byteArray = new byte[photo.getImage().length];
            int i = 0;
            for (Byte wrappedByte : photo.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            try {
                IOUtils.copy(is, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Photo with id: " + imageId +  " Not Found", exc);
        }
    }

    @ExceptionHandler(UnHandledExceptions.class)
    @PostMapping("/image/upload")
    public String handleImagePost(@RequestParam("imagefile") MultipartFile file){
        try {
            photoService.saveImage(file);
            return "redirect:/photos";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Gallery with id: Not Found", exc);
        }
    }

    @PostMapping("/image/{id}/delete")
    public String deleteGallery(@PathVariable String id){
        try {
            Photo photo = photoRepo.findById(Long.valueOf(id)).get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user=userRepo.findByEmail(userDetails.getUsername());
            List<Photo> userPhotos=user.getUserPhotos();
            userPhotos.remove(photo);
            user.setUserPhotos(userPhotos);
            List<Gallery> usergalleries=user.getUserGalleries();
            usergalleries.forEach(gallery -> {
                List<Photo> photos = gallery.getGalleryPhotos();
                photos.remove(photo);
                gallery.setGalleryPhotos(photos);
            });
            photoRepo.delete(photo);
            return "redirect:/photos";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Photo with id: " + id +  " Not Found", exc);
        }
    }
}
