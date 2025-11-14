package com.example.identity_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PriceFilterRequest {
    List<Long> productIds;
    Double minPrice;
    Double maxPrice;
}
