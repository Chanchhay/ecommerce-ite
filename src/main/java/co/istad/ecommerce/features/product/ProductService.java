package co.istad.ecommerce.features.product;

import co.istad.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.ecommerce.features.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    /**
     * Create a new product
     * @param createProductRequest is requesting data for creating product
     * @return {@link ProductResponse}
     * @author chanchhay
     * @since 23-June-2026
     */
    ProductResponse createNew(CreateProductRequest createProductRequest);
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
}
