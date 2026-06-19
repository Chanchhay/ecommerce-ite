package co.istad.ecommerce.model.mapper.category;

import co.istad.ecommerce.model.domain.Product;
import co.istad.ecommerce.model.dto.product.ProductRequest;
import co.istad.ecommerce.model.dto.product.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product mapProductRequestToProduct (ProductRequest productRequest);
    ProductResponse mapProductToProductResponse (Product product);
}
