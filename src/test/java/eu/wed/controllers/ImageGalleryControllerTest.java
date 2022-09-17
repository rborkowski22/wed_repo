package eu.wed.controllers;

import eu.wed.model.Image;
import eu.wed.repositories.ImageRepository;
import eu.wed.services.ImageService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.assertj.core.api.Assertions;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.testng.annotations.Test;

import java.util.Optional;

public class ImageGalleryControllerTest {

    @Tested
    ImageGalleryController imageGalleryController;

    @Injectable
    ImageRepository imageRepository;
    @Injectable
    ImageService imageService;
    @Injectable
    Model model;

    @Test
    public void getHomeTest() {
        //given
        Page<Image> imagePage = Page.empty();

        Optional<Integer> pageOptional = Optional.of(2);
        Optional<Integer> sizeOptional = Optional.of(12);
        new Expectations() {{
            ImageGalleryControllerTest.this.imageService.getPaginatedImagesAndAddToModel(pageOptional,  model);
            this.result = imagePage;
        }};
        //when
        String result = this.imageGalleryController.getHomePage(this.model, pageOptional);
        //then
        Assertions.assertThat(result).isEqualTo("index");
        new Verifications(){{
            ImageGalleryControllerTest.this.imageService.getPaginatedImagesAndAddToModel(pageOptional, model);
            this.times =1;
        }};
    }
}
