package eu.wed.services;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {

    void getPaginatedImagesAndAddToModel(Optional<Integer> pageNumber, Model model);

    ResponseEntity<?> uploadFiles(MultipartFile[] files);
}
