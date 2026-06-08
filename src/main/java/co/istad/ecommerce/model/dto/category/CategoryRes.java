package co.istad.ecommerce.model.dto.category;

import co.istad.ecommerce.model.domain.Category;
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
