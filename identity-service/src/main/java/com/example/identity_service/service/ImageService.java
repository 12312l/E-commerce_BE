package com.example.identity_service.service;

import com.example.identity_service.entity.Image;
import com.example.identity_service.entity.ProductVariant;
import com.example.identity_service.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
    ImageRepository imageRepository;

    @NonFinal
    @Value("${app.file.upload-dir}")
    String FOLDER_PATH;

    @NonFinal
    @Value("${server.image.url}")
    String baseUrl;

    public String uploadImagesToFileSystem(List<MultipartFile> files, Long variant_id) throws IOException {

        StringBuilder resultMessage = new StringBuilder();

        for (MultipartFile file : files) {
            // Tạo đường dẫn cho từng file
            String filePath = FOLDER_PATH + file.getOriginalFilename();

            // Lưu thông tin file vào database
            Image image = imageRepository.save(Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath)
                    .url(baseUrl+file.getOriginalFilename())
                    .productVariant(
                            ProductVariant.builder().variantId(variant_id).build())
                    .build());

            // Lưu file vào hệ thống tệp
            file.transferTo(new File(filePath));

            // Thêm thông báo thành công cho từng file
            resultMessage
                    .append("File uploaded successfully: ")
                    .append(filePath)
                    .append("\n");
            // Trả về thông báo nếu có ít nhất một file được upload thành công

        }
        if (resultMessage.length() > 0) {
            return resultMessage.toString();
        }

        return "No files were uploaded.";
    }
}
