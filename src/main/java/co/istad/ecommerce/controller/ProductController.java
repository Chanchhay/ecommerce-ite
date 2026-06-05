package co.istad.ecommerce.controller;


import co.istad.ecommerce.model.domain.Product;
import co.istad.ecommerce.model.dto.product.ProductResponse;
import co.istad.ecommerce.model.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
