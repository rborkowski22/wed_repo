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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        int page = 2;
        int size = 12;
        new Expectations() {{
            ImageGalleryControllerTest.this.imageService.getPaginatedImages((Pageable) any);
            this.result = imagePage;
        }};
        //when
        String result = this.imageGalleryController.getHomePage(this.model, Optional.of(page),Optional.of(size));
        //then
        Assertions.assertThat(result).isEqualTo("index");
        new Verifications(){{
            ImageGalleryControllerTest.this.model.addAttribute(anyString,any);
            this.times = 2;
            ImageGalleryControllerTest.this.imageService.getPaginatedImages(PageRequest.of(page-1,size));
            this.times =1;
        }};
    }
}
