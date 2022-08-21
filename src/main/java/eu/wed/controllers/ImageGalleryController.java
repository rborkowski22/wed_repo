package eu.wed.controllers;

import eu.wed.model.Image;
import eu.wed.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGalleryController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping({"", "/", "/index", "/home"})
    public String getHomePage() {
        return "index";
    }

    @GetMapping({"/test"})
    public String test() {
        Image image = new Image("testName", null, 1L);
        this.imageRepository.save(image);
        return "test";
    }


}
