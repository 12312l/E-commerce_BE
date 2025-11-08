package com.example.identity_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DeliveryMethod {
    FAST("Giao hàng nhanh"),
    EXPRESS("Giao hàng hỏa tốc");

    private final String vietnamese;

    DeliveryMethod(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    @JsonValue
    public String getVietnamese() {
        return vietnamese;
    }
}
