package eu.wed.services;

import eu.wed.model.Image;
import eu.wed.repositories.ImageRepository;
import eu.wed.utils.DefaultValuesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ImageServiceImpl implements ImageService{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void getPaginatedImagesAndAddToModel(Optional<Integer> pageNumber, Model model) {
        int currentPage = pageNumber.orElse(1);
        int size = DefaultValuesUtil.IMAGES_PER_PAGE_SIZE;
        Pageable pageable = PageRequest.of(currentPage - 1, size);

        Page<Image> paginatedImages = this.imageRepository.findAll(pageable);

        model.addAttribute("imagesPage", paginatedImages);

        if (paginatedImages.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, paginatedImages.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    @Override
    public @ResponseBody ResponseEntity<?> uploadFiles(MultipartFile[] files) {
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
