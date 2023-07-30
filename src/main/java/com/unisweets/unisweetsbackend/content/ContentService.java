package com.unisweets.unisweetsbackend.content;

import com.unisweets.unisweetsbackend.picture.Picture;
import com.unisweets.unisweetsbackend.picture.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ContentService {
    private static final String FOLDER_PATH = "C:\\Users\\TeaChan\\IdeaProjects\\UniSweetsBackEnd\\src\\main\\resources\\static\\images\\";
    private final PictureRepository pictureRepository;

    public Picture uploadImage(MultipartFile multipartFile) throws IOException {
        Assert.state(!multipartFile.isEmpty(), "File is empty!");
        Picture picture = new Picture();
        multipartFile.transferTo(new File(FOLDER_PATH + picture.getPictureName()));
        pictureRepository.save(picture);
        return pictureRepository.save(picture);
    }

    public void deleteImage(String imageName){
        File imageFile = new File(FOLDER_PATH + imageName);
        imageFile.delete();
    }
}
