package eu.wed.services;

import eu.wed.model.Image;
import eu.wed.repositories.ImageRepository;
import eu.wed.utils.DefaultValuesUtil;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

public class ImageServiceImplTest {

    @Tested
    ImageServiceImpl imageService;

    @Injectable
    ImageRepository imageRepository;
    @Injectable
    Model model;

    @DataProvider(name = "getPaginatedImages")
    Object[][] getPaginatedImages(){
      return new Object[][]{
              {Optional.empty(),Optional.empty(), 2},
              {Optional.of(1), Optional.of(12), 4},
              {Optional.of(3), Optional.of(15), 1}
      };
    }

    @Test(dataProvider = "getPaginatedImages")
    public void testGetPaginatedImagesAndAddToModel(Optional<Integer> pageNumber, Optional<Integer> pageSize,
                                                    int totalPagesNumber) {
        //given
        Pageable pageable = Pageable.ofSize(pageSize.orElse(DefaultValuesUtil.IMAGES_PER_PAGE_SIZE));
        Page<Image> paginatedImages = Page.empty();
        int addAttributeCalls = totalPagesNumber > 0 ? 2 : 1;
        new Expectations() {{
            ImageServiceImplTest.this.imageRepository.findAll((Pageable) any);
            this.result = paginatedImages;
        }};
        //when
        this.imageService.getPaginatedImagesAndAddToModel(pageNumber, model);
        //then
        new Verifications(){{
           ImageServiceImplTest.this.imageRepository.findAll((Pageable) any);
           this.times = 1;
           ImageServiceImplTest.this.model.addAttribute(anyString, any);
           this.times = addAttributeCalls;
        }};
    }

    @Test
    public void testUploadFiles(){
        //given
        String fileName1 = "file1";
        String fileName2 = "file2";
        byte[] content1 = {0,1,2,3};
        byte[] content2 = {0,1,2,3,4};
        MockMultipartFile multipartFile1 = new MockMultipartFile(fileName1, content1);
        MockMultipartFile multipartFile2 = new MockMultipartFile(fileName2, content2);
        MultipartFile[] files = {multipartFile1, multipartFile2};

        //when
        this.imageService.uploadFiles(files);
        //then
        new Verifications() {{
            ImageServiceImplTest.this.imageRepository.save((Image)any);
            this.times = 2;
        }};
    }
}
