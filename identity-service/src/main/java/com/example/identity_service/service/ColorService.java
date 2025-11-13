package com.example.identity_service.service;

import com.example.identity_service.dto.request.ColorRequest;
import com.example.identity_service.dto.response.ColorResponse;
import com.example.identity_service.entity.Color;
import com.example.identity_service.mapper.ColorMapper;
import com.example.identity_service.repository.ColorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ColorService {
    ColorMapper colorMapper;
    ColorRepository colorRepository;

    public ColorResponse createColor(ColorRequest colorRequest){
        List<Color> colors = colorRepository.findAll();

        for (Color color: colors
             ) {
            if(colorRequest.getCode().equalsIgnoreCase(color.getCode())){
                throw  new RuntimeException("Mã màu đã tồn tại!");
            }
        }

        if (!isHexColor(colorRequest.getCode())) {
            throw new RuntimeException("Mã màu không hợp lệ! Vui lòng nhập mã màu HEX hợp lệ.");
        }

        Color color = colorMapper.toColor(colorRequest);
        Color saveColor = colorRepository.save(color);
        return colorMapper.toColorResponse(saveColor);
    }

    public List<ColorResponse> getAllColor(){
        return
                colorRepository.findAll()
                        .stream()
                        .map(colorMapper::toColorResponse)
                        .toList();
    }


    public boolean isHexColor(String colorCode) {
        // Kiểm tra nếu mã màu có dạng # và theo sau là 6 ký tự hợp lệ (0-9, A-F, a-f)
        String regex = "^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$";
        return colorCode.matches(regex);
    }
}
