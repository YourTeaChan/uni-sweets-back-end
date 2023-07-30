package com.unisweets.unisweetsbackend.content;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("api/v1/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping("{imageName}")
    public ResponseEntity<?> getImageByName(@PathVariable String imageName) throws IOException {
        File imageFile = new File("src/main/resources/static/images/" + imageName);
        String contentType = Files.probeContentType(imageFile.toPath());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(Files.readAllBytes(imageFile.toPath()));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile image) throws IOException {
        return ResponseEntity.ok(contentService.uploadImage(image));
    }

    @DeleteMapping("{imageName}")
    public void deleteImage(@PathVariable String imageName) {
        contentService.deleteImage(imageName);
    }
}
