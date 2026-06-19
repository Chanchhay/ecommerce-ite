package co.istad.ecommerce.controller;


import co.istad.ecommerce.model.domain.Product;
import co.istad.ecommerce.model.dto.category.CategoryRes;
import co.istad.ecommerce.model.dto.filter.RequestDto;
import co.istad.ecommerce.model.dto.product.ProductResponse;
import co.istad.ecommerce.model.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Map<String, List<ProductResponse>> getAllProduct() {
        return Map.of("products", productService.getAllProduct());
    }

    @PostMapping("/dynamic")
    public Page<ProductResponse> searchDynamic(@RequestBody RequestDto requestDto, @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        return productService.dynamicProductSearch(requestDto.getSearchRequestDto(), pageable, requestDto.getGlobalOperator());
    }
}
