package co.istad.ecommerce.features.category.mapper;

import co.istad.ecommerce.features.category.Category;
import co.istad.ecommerce.features.category.dto.CategoryRes;
import co.istad.ecommerce.features.category.dto.CreateCategoryReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapCreateCategoryRequestToCategory(CreateCategoryReq categoryReq);
    CategoryRes mapCategoryToCategoryRes(Category category);
}
