package eu.wed.services;

import eu.wed.model.Image;
import eu.wed.repositories.ImageRepository;
import mockit.Injectable;
import mockit.Tested;
import org.assertj.core.api.Assertions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.annotations.Test;


public class ImageServiceTest {

    @Injectable
    ImageRepository imageRepository;

    @Tested
    ImageService imageService;

    @Test
    public void testGetPaginatedImages() {
        int x = 1;

        Pageable pageable = PageRequest.of(1,12);
        Page<Image> paginatedImages = this.imageService.getPaginatedImages(pageable);

        Assertions.assertThat(paginatedImages).isNotNull();
    }
}
