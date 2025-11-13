package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.ColorRequest;
import com.example.identity_service.dto.response.ColorResponse;
import com.example.identity_service.service.ColorService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ColorController {
    ColorService colorService;

    @PostMapping("/add_color")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<ColorResponse> createColor(@RequestBody ColorRequest colorRequest){
        return ApiResponse.<ColorResponse>builder()
                .result(colorService.createColor(colorRequest))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<List<ColorResponse>> getAllColor(){
        return ApiResponse.<List<ColorResponse>>builder()
                .result(colorService.getAllColor())
                .build();
    }
}
