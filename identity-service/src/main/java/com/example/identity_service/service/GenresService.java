package com.example.identity_service.service;

import com.example.identity_service.entity.Category;
import com.example.identity_service.entity.Genres;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.repository.CategoryRepository;
import com.example.identity_service.repository.GenresRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GenresService {
    GenresRepository genresRepository;
    CategoryRepository categoryRepository;
    public List<Genres> getAllGenres() {
        return genresRepository.findAll();
    }

    public List<Genres> getGenresById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));

        return genresRepository.findAllByCategory_CategoryId(category.getCategoryId());
    }
}
