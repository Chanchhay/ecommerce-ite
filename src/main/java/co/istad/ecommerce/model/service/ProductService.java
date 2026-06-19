package co.istad.ecommerce.model.service;

import co.istad.ecommerce.model.dto.category.CategoryRes;
import co.istad.ecommerce.model.dto.filter.RequestDto;
import co.istad.ecommerce.model.dto.filter.SearchRequestDto;
import co.istad.ecommerce.model.dto.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProduct();
    Page<ProductResponse> dynamicProductSearch(List<SearchRequestDto> searchRequestDto, Pageable pageable, RequestDto.GlobalOperator globalOperator);
}
