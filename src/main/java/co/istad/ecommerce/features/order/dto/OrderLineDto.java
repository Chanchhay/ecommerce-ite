package co.istad.ecommerce.features.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderLineDto(
        @NotBlank(message = "code must not be blank")
        String code,
        @NotNull(message = "qty must not be null")
        @Positive(message = "Qty must be positive")
        Integer qty,
        @Positive(message = "Unit price must be positive")
        @NotNull(message = "unit price must not be null")
        BigDecimal unitPrice
) {
}