package eu.wed.controllers;

import eu.wed.model.Image;
import eu.wed.repositories.ImageRepository;
import eu.wed.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ImageGalleryController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @GetMapping({"", "/", "/index", "/home"})
    public String getHomePage(Model model, @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);

        Page<Image> imagesPage = this.imageService.getPaginatedImages(PageRequest.of(currentPage - 1,pageSize));
        model.addAttribute("imagesPage", imagesPage);

        if (imagesPage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, imagesPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

    @GetMapping({"/test"})
    public String test() {
        Image image = new Image("testName", null);
        this.imageRepository.save(image);
        return "test";
    }

    @GetMapping(value = "/upload")
    public String getUploadPage() {
        return "admin";
    }

    @PostMapping("upload")
    public @ResponseBody ResponseEntity<?> uploadFiles(final @RequestParam("files") MultipartFile[] files) {
        try {
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                byte[] imageData = file.getBytes();
                Image image = new Image(filename, imageData);
                this.imageRepository.save(image);
                log.info("Image with name " + filename + " uploaded successfully");
            }
            return new ResponseEntity<>("Files saved", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
