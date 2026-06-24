package co.istad.ecommerce.features.category;

import co.istad.ecommerce.features.category.dto.CategoryRes;
import co.istad.ecommerce.features.category.dto.CreateCategoryReq;
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
    public CategoryRes createCategory(@Valid @RequestBody CreateCategoryReq categoryReq){
        return categoryService.createCategory(categoryReq);
    }

    @GetMapping
    public Page<CategoryRes> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{id}")
    public CategoryRes getCategoryById(@PathVariable Integer id){
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteCategory(@PathVariable Integer id){
        categoryService.softDeleteCategory(id);
    }

    @PatchMapping("/{id}")
    public CategoryRes updateCategory(@PathVariable Integer id, @RequestBody CreateCategoryReq categoryReq){
        return categoryService.updateCategory(id, categoryReq);
    }

    @GetMapping("/{id}/subcategories")
    public List<CategoryRes> getSubcategories(@PathVariable Integer id){
        return categoryService.getSubcategories(id);
    }


}