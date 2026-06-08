package co.istad.ecommerce.model.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateCategoryReq(
        @NotBlank(message = "name is required")
        @Size(min = 3, max = 50)
        String name,
        @NotBlank(message = "cannot be blank")
        String description,
        @Size(max = 255)
        String icon,
        @Positive
        Integer parentCategoryId
) {
}
