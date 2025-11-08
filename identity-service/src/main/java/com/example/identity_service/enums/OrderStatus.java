package com.example.identity_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    PENDING("Chờ xử lý"),
    PROCESSING("Đang xử lý"),
    SHIPPED("Đang giao hàng"),
    DELIVERED("Đã giao"),
    CANCELLED("Đã hủy"),
    RETURNED("Đã trả hàng");

    private final String vietnamese;

    OrderStatus(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    @JsonValue
    public String getVietnamese() {
        return vietnamese;
    }
}

