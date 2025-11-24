package com.example.identity_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentMethod {
    COD,
    BANK_TRANSFER,
    E_WALLET,
    CREDIT_CARD,
    VNPAY

}

