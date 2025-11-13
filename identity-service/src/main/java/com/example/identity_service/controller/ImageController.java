package com.example.identity_service.controller;

import com.example.identity_service.service.ImageService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageController {
    ImageService imageService;

    @PostMapping(value = "images/upload/" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImagesToFileSystem(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("variant_id") Long variant_id
            )
    throws IOException {
        String uploadImages = imageService.uploadImagesToFileSystem(files, variant_id);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImages);
    }
}
