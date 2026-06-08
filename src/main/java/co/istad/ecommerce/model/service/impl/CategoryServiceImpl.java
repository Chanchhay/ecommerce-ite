package co.istad.ecommerce.model.service.impl;

import co.istad.ecommerce.model.domain.Category;
import co.istad.ecommerce.model.dto.category.CategoryRes;
import co.istad.ecommerce.model.dto.category.CreateCategoryReq;
import co.istad.ecommerce.model.mapper.category.CategoryMapper;
import co.istad.ecommerce.model.repository.CategoryRepo;
import co.istad.ecommerce.model.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryRes createCategory(CreateCategoryReq categoryReq) {
        log.info("create new: {}", categoryReq);

        boolean isExisting = categoryRepo.existsByName(categoryReq.name());
        if (isExisting) throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already existed");

        Category parentCategory = null;

        if (categoryReq.parentCategoryId() != null) {
            parentCategory = categoryRepo.findById(categoryReq.parentCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent category not found"));
        }

        Category category = categoryMapper.mapCreateCategoryRequestToCategory(categoryReq);
        category.setParentCategory(parentCategory);

        categoryRepo.save(category);

        return categoryMapper.mapCategoryToCategoryRes(category);
    }

    @Override
    public Page<CategoryRes> getAllCategories(Pageable pageable) {
        return categoryRepo.findAll(pageable).map(categoryMapper::mapCategoryToCategoryRes
                );
    }

    @Override
    public CategoryRes getCategoryById(Integer id) {
        return categoryMapper.mapCategoryToCategoryRes(categoryRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")));
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepo.delete(category);
    }

    @Override
    public void softDeleteCategory(Integer id) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        category.setIsDeleted(true);
        categoryRepo.save(category);
    }

    @Override
    public CategoryRes updateCategory(Integer id, CreateCategoryReq categoryReq) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        category.setName(categoryReq.name());
        category.setDescription(categoryReq.description());
        category.setIcon(categoryReq.icon());
        Category updatedCategory = categoryRepo.save(category);

        return categoryMapper.mapCategoryToCategoryRes(updatedCategory);
    }

    @Override
    public List<CategoryRes> getSubcategories(Integer id) {
        List<Category> subcategories = categoryRepo.getCategoriesByParentCategoryId(id);
        return subcategories.stream().map(categoryMapper::mapCategoryToCategoryRes).toList();
    }


}
