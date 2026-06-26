package co.istad.ecommerce.features.order.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank(message = "Address is required")
        String address,
        @NotNull
        @Positive(message = "Discount must be positive")
        @Max(value = 100, message = "Discount must not be greater than 100")
        Double discount,
        @Size(max = 255)
        String remark,
        @NotEmpty(message= "Order Line is required")
        List<OrderLineDto> orderLines
) {
}