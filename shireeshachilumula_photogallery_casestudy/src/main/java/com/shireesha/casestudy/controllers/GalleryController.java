package com.shireesha.casestudy.controllers;

import com.shireesha.casestudy.exceptions.GalleryResourceException;
import com.shireesha.casestudy.exceptions.UnHandledExceptions;
import com.shireesha.casestudy.models.Gallery;
import com.shireesha.casestudy.models.Photo;
import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.repositories.GalleryRepository;
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
import java.util.*;

@Controller
public class GalleryController {

    private final PhotoService photoService;
    @Autowired
    private GalleryRepository galleryRepo;

    @Autowired
    private PhotoRepository photoRepo;

    @Autowired
    private UserRepository userRepo;

    public GalleryController(PhotoService photoService) {
        this.photoService = photoService;
    }

    
    //displaying user galleries
    @GetMapping("/galleries")
    public String listUserGalleries(Model model) {
    	//Retrieving user details using spring security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        //Retrieving list of galleries associated with user by email id
        List<Gallery> listGalleries = userRepo.findByEmail(userDetails.getUsername()).getUserGalleries();
        model.addAttribute("galleries", listGalleries);
       
        return "user_galleries";
    }

    //creating new gallery using the query parameter "galleryname"
    @PostMapping("/gallery/create")
    public String handleImagePost(@RequestParam("galleryname") String name) {
        try {
        	//Retrieving user details using spring security
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Gallery gallery = new Gallery();
            //setting gallery name from browser input
            gallery.setName(name);
            User user=userRepo.findByEmail(userDetails.getUsername());
            List<Gallery> usergalleries=user.getUserGalleries();
            //Associating gallery and user details
            usergalleries.add(gallery);
            user.setUserGalleries(usergalleries);
            gallery.setUsers(new HashSet<User>(Collections.singletonList(user)));
            //saving gallery details
            galleryRepo.save(gallery);
            return "redirect:/galleries";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Gallery with name: " + name +  " not saved", exc);
        }
    }
    //editing gallery name using the query parameter "galleryname"
    @PostMapping("/gallery/{id}/rename")
    public String handleImagePost(@PathVariable String id, @RequestParam("galleryname") String name) {
        try {
        	/* findById() method returns an Optional instance so the get() method is used to 
    		 * retrieve the actual Gallery object.
    		 */
            Gallery gallery = galleryRepo.findById(Long.valueOf(id)).get();
            gallery.setName(name);
            //saving edited gallery name into database
            galleryRepo.save(gallery);
            return "redirect:/gallery/" + id + "/show";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Gallery with id: " + id +  " Not Found", exc);
        }
    }

    @PostMapping("/gallery/search")
    public String searchAlbum(@RequestParam("albumName") String name, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            List<Gallery> galleries = galleryRepo.findByNameContainingAndUsersIn(name, Arrays.asList(userRepo.findByEmail(userDetails.getUsername())));
            model.addAttribute("galleries", galleries);
            return "user_galleries";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Gallery with name: " + name +  " Not Found", exc);
        }
    }

    @GetMapping("/gallery/{id}/show")
    public String showById(@PathVariable String id, Model model){
        try {
        	/* findById() method returns an Optional instance so the get() method is used to 
    		 * retrieve the actual Gallery object.
    		 */
            model.addAttribute("gallery", galleryRepo.findById(Long.valueOf(id)).get());
            return "gallery";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Gallery with id: " + id +  " Not Found", exc);
        }
    }
    //saving image to gallery
    @ExceptionHandler(UnHandledExceptions.class)
    @PostMapping("/gallery/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){
        try {
            photoService.saveImage(Long.valueOf(id), file);
            return "redirect:/gallery/" + id + "/show";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Gallery with id: " + id +  " Not Found", exc);
        }
    }

    @SuppressWarnings("unused")
	@GetMapping("/gallery/{id}/image/{imageId}")
    public void renderImageFromDB(@PathVariable String id, @PathVariable String imageId, HttpServletResponse response) throws IOException {
        try {
        	/* findById() method returns an Optional instance so the get() method is used to 
    		 * retrieve the actual Gallery object.
    		 */
            Gallery gallery = galleryRepo.findById(Long.valueOf(id)).get();
            Photo photo = photoRepo.findById(Long.valueOf(imageId)).get();
           // System.out.println("Found image" + photo.getName());
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
            throw new GalleryResourceException("Gallery with id: " + id +  " Not Found", exc);
        }
    }

    //deleteing selected gallery
    @PostMapping("/gallery/{id}/delete")
    public String deleteGallery(@PathVariable String id){
        try {
        	/* findById() method returns an Optional instance so the get() method is used to 
    		 * retrieve the actual Gallery object.
    		 */
            Gallery gallery = galleryRepo.findById(Long.valueOf(id)).get();
          //Retrieving user details using spring security
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user=userRepo.findByEmail(userDetails.getUsername());
            List<Gallery> usergalleries=user.getUserGalleries();
            usergalleries.remove(gallery);
            user.setUserGalleries(usergalleries);
            //deleting seleted gallery from database
            galleryRepo.delete(gallery);
            return "redirect:/galleries";
        } catch (GalleryResourceException exc) {
            throw new GalleryResourceException("Gallery with id: " + id +  " Not Found", exc);
        }
    }
    //Deleting all galleries for user 
    @PostMapping("/gallery/deleteall")
    public String deleteAllGalleries(){
        List<Gallery> galleries = galleryRepo.findAll();
      //Retrieving user details using spring security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      //Retrieving list of galleries associated with user by email id
        User user=userRepo.findByEmail(userDetails.getUsername());
        List<Gallery> usergalleries=user.getUserGalleries();
        //removing all galleries accociated with user
        usergalleries.removeAll(galleries);
        user.setUserGalleries(Collections.emptyList());
        galleryRepo.deleteAll();
        return "redirect:/galleries";
    }
}
