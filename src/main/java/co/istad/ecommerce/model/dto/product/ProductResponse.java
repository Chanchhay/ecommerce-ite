package co.istad.ecommerce.model.dto.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String uuid,
        String code,
        String name,
        String description,
        String thumbnail,
        BigDecimal unitPrice,
        Integer qty,
        Boolean isAvailable,
        Boolean isDelete,
        Integer categoryId
) {
}
