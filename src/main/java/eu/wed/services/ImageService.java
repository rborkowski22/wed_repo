package eu.wed.services;

import eu.wed.model.Image;
import eu.wed.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Page<Image> getPaginatedImages(Pageable pageable) {
        return this.imageRepository.findAll(pageable);
    }

}
