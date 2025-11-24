package com.example.identity_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {
    PAID,
    UNPAID,
    PENDING,
    REFUNDED
}

