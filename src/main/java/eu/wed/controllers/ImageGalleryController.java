package eu.wed.controllers;

import eu.wed.model.Image;
import eu.wed.repositories.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ImageGalleryController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

    @GetMapping(value = "/upload")
    public String getUploadPage() {
        return "admin";
    }

    @PostMapping("upload")
    public @ResponseBody ResponseEntity<?> uploadFiles(final @RequestParam("image") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            byte[] imageData = file.getBytes();
            Image image = new Image(filename, imageData, 0L);
            this.imageRepository.save(image);

            //TODO implement
            log.info("***** uploading image");
            return new ResponseEntity<>("Files saved", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
