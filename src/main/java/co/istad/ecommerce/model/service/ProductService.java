package co.istad.ecommerce.model.service;

import co.istad.ecommerce.model.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProduct();
}
