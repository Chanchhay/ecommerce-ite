package co.istad.ecommerce.controller;

import co.istad.ecommerce.model.dto.category.CategoryRes;
import co.istad.ecommerce.model.dto.category.CreateCategoryReq;
import co.istad.ecommerce.model.dto.filter.RequestDto;
import co.istad.ecommerce.model.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryRes createCategory(@Valid @RequestBody CreateCategoryReq categoryReq) {
        return categoryService.createCategory(categoryReq);
    }

    @GetMapping
    public Page<CategoryRes> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{id}")
    public CategoryRes getCategoryById(
            @PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteCategory(@PathVariable Integer id) {
        categoryService.softDeleteCategory(id);
    }

    @PatchMapping("/{id}")
    public CategoryRes updateCategory(@PathVariable Integer id, @RequestBody CreateCategoryReq categoryReq) {
        return categoryService.updateCategory(id, categoryReq);
    }

    @GetMapping("/{id}/subcategories")
    public List<CategoryRes> getSubcategories(@PathVariable Integer id) {
        return categoryService.getSubcategories(id);
    }

    @GetMapping("/search")
    public Page<CategoryRes> searchCategory(@RequestParam(required = false) String name, @RequestParam(required = false) String description, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.searchCategories(name, description, pageable);
    }

    @PostMapping("/dynamic")
    public Page<CategoryRes> searchDynamic(@RequestBody RequestDto requestDto, @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.dynamicSearch(requestDto.getSearchRequestDto(), pageable, requestDto.getGlobalOperator());
    }
}
