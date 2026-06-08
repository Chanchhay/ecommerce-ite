package co.istad.ecommerce.model.service;

import co.istad.ecommerce.model.dto.category.CategoryRes;
import co.istad.ecommerce.model.dto.category.CreateCategoryReq;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryRes createCategory(CreateCategoryReq categoryReq);

    Page<CategoryRes> getAllCategories(Pageable pageable);

    CategoryRes getCategoryById(Integer id);

    void deleteCategory(Integer id);

    void softDeleteCategory(Integer id);

    CategoryRes updateCategory(Integer id, CreateCategoryReq categoryReq);

    List<CategoryRes> getSubcategories(Integer id);

}
