package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserRespone;
import com.example.identity_service.entity.User;
import com.example.identity_service.service.UserSevice;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserSevice userSevice;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userSevice.createUser(request));
        return apiResponse;
    }

    @GetMapping
    List<User> getUsers(){
        return userSevice.getUsers();
    }

    @GetMapping("/{userId}")
    UserRespone getUser(@PathVariable("userId") String userId){
        return userSevice.getUser(userId);
    }

    @PutMapping("/{userId}")
    UserRespone updateUser(@PathVariable String userId,@RequestBody UserUpdateRequest request) {
        return userSevice.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId){
        userSevice.deleteUser(userId);
        return "User has been deleted";
    }
}
