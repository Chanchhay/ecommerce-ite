package co.istad.ecommerce.features.category;

import co.istad.ecommerce.features.category.dto.CategoryRes;
import co.istad.ecommerce.features.category.dto.CreateCategoryReq;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface CategoryService {
    CategoryRes createCategory(CreateCategoryReq categoryReq);

    Page<CategoryRes> getAllCategories(Pageable pageable);

    Page<CategoryRes> getAllCategories(org.springframework.data.domain.Pageable pageable);

    CategoryRes getCategoryById(Integer id);

    void deleteCategory(Integer id);

    void softDeleteCategory(Integer id);

    CategoryRes updateCategory(Integer id, CreateCategoryReq categoryReq);

    List<CategoryRes> getSubcategories(Integer id);

}
