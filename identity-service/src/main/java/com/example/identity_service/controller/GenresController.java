package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.entity.Genres;
import com.example.identity_service.service.GenresService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenresController {
    GenresService genresService;

    @GetMapping
    ApiResponse<List<Genres>> getAllGenres() {
        return ApiResponse.<List<Genres>>builder()
                .result(genresService.getAllGenres())
                .build();
    }

    @GetMapping("/{categoryId}")
    ApiResponse<List<Genres>> getGenresByCategoryId(@PathVariable("categoryId") Long id) {
        return ApiResponse.<List<Genres>>builder()
                .result(genresService.getGenresById(id))
                .build();
    }
}
