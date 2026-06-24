package co.istad.ecommerce.features.category.dto;

import lombok.Builder;

@Builder
public record CategoryRes(
        String name,
        String description,
        String icon,
        Boolean isDeleted,
        CategoryRes parentCategory
) {
}
