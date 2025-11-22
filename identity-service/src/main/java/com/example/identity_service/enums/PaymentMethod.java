package com.example.identity_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentMethod {
    COD("Thanh toán khi nhận hàng"),
    BANK_TRANSFER("Chuyển khoản ngân hàng"),
    E_WALLET("Ví điện tử (Momo, ZaloPay, v.v.)"),
    CREDIT_CARD("Thẻ tín dụng (Visa, MasterCard)"),
    VNPAY("Thanh toán qua VNPay");

    private final String vietnamese;

    PaymentMethod(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    @JsonValue
    public String getVietnamese() {
        return vietnamese;
    }

    // Add method convert vietnamese → Enum
    public static PaymentMethod fromVietnamese(String text) {
        for (PaymentMethod pm : PaymentMethod.values()) {
            if (pm.vietnamese.equals(text)) {
                return pm;
            }
        }
        throw new IllegalArgumentException("Không tồn tại phương thức thanh toán: " + text);
    }


}

