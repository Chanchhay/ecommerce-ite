package co.istad.ecommerce.model.service.impl;

import co.istad.ecommerce.model.domain.Product;
import co.istad.ecommerce.model.dto.product.ProductResponse;
import co.istad.ecommerce.model.repository.ProductRepo;
import co.istad.ecommerce.model.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> product = productRepo.findAll();
        return product.stream().map(p -> ProductResponse.builder().uuid(p.getUuid()).code(p.getCode()).name(p.getName()).description(p.getDescription()).thumbnail(p.getThumbnail()).unitPrice(p.getUnitPrice()).qty(p.getQty()).isAvailable(p.getIsAvailable()).isDelete(p.getIsDelete()).categoryId(p.getCategory().getId()).build()).toList();
    }
}
