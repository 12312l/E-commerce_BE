package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.AuthenticationRequest;
import com.example.identity_service.dto.request.IntrospectRequest;
import com.example.identity_service.dto.response.AuthenticationRespone;
import com.example.identity_service.dto.response.IntrospectRespone;
import com.example.identity_service.dto.response.UserRespone;
import com.example.identity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationRespone> authenticationReponse(@RequestBody AuthenticationRequest authenticationRequest) {
        ApiResponse<AuthenticationRespone> response = new ApiResponse<>();
        AuthenticationRespone authenticationRespone = authenticationService.authenticate(authenticationRequest);
        response.setResult(authenticationRespone);

        return response;
    }

    @PostMapping ("/token")
    ApiResponse<AuthenticationRespone> authenticationResponeApiResponse(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationRespone>builder()
                        .result(result)
                .build();
    }

    @PostMapping ("/introspect")
    ApiResponse<IntrospectRespone> authenticationResponeApiResponse(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectRespone>builder()
                .result(result)
                .build();
    }
}
