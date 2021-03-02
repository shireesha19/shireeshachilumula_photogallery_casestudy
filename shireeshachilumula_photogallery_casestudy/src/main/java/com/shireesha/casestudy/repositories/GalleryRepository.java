package com.shireesha.casestudy.repositories;

import java.util.List;

import com.shireesha.casestudy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shireesha.casestudy.models.Gallery;

public interface GalleryRepository  extends JpaRepository<Gallery, Long> {

    List<Gallery> findByNameContainingAndUsersIn(String name, List<User> user);

}
