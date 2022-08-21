package eu.wed.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGalleryController {

    @GetMapping({"", "/", "/index", "/home"})
    public String getHomePage() {
        return "index";
    }
}
