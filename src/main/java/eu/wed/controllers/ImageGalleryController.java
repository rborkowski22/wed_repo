package eu.wed.controllers;

import eu.wed.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
public class ImageGalleryController {

    private final ImageService imageService;

    @Autowired
    public ImageGalleryController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping({"", "/"})
    public String getHomePage(Model model, @RequestParam("page") Optional<Integer> page) {
        this.imageService.getPaginatedImagesAndAddToModel(page, model);
        return "index";
    }

    @GetMapping(value = "/upload")
    public String getUploadPage() {
        return "admin";
    }

    @PostMapping("upload")
    public @ResponseBody ResponseEntity<?> uploadFiles(final @RequestParam("files") MultipartFile[] files) {
        return this.imageService.uploadFiles(files);
    }
}
