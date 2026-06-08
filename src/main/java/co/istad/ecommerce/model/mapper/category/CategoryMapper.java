package co.istad.ecommerce.model.mapper.category;

import co.istad.ecommerce.model.domain.Category;
import co.istad.ecommerce.model.dto.category.CategoryRes;
import co.istad.ecommerce.model.dto.category.CreateCategoryReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapCreateCategoryRequestToCategory(CreateCategoryReq categoryReq);
    CategoryRes mapCategoryToCategoryRes(Category category);
}
