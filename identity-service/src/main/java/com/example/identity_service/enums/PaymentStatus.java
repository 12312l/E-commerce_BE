package com.example.identity_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {
    PAID("Đã thanh toán"),
    UNPAID("Chưa thanh toán"),
    PENDING("Đang chờ xử lý"),
    REFUNDED("Đã hoàn tiền");

    private final String vietnamese;

    PaymentStatus(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    @JsonValue
    public String getVietnamese() {
        return vietnamese;
    }

    // Add method convert vietnamese → Enum
    public static PaymentStatus fromVietnamese(String text) {
        for (PaymentStatus ps : PaymentStatus.values()) {
            if (ps.vietnamese.equals(text)) {
                return ps;
            }
        }
        throw new IllegalArgumentException("Không tồn tại trạng thái thanh toán: " + text);
    }
}

