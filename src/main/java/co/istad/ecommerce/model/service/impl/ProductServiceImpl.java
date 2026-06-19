package co.istad.ecommerce.model.service.impl;

import co.istad.ecommerce.model.domain.Product;
import co.istad.ecommerce.model.dto.filter.RequestDto;
import co.istad.ecommerce.model.dto.filter.SearchRequestDto;
import co.istad.ecommerce.model.dto.product.ProductResponse;
import co.istad.ecommerce.model.mapper.category.ProductMapper;
import co.istad.ecommerce.model.repository.ProductRepo;
import co.istad.ecommerce.model.service.ProductService;
import co.istad.ecommerce.model.service.specification.FilterSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final FilterSpecification<Product> filterSpecification;

    public List<ProductResponse> getAllProduct() {
        List<Product> product = productRepo.findAll();
        return product.stream().map(p -> ProductResponse.builder()
                .uuid(p.getUuid())
                .code(p.getCode())
                .name(p.getName())
                .description(p.getDescription())
                .thumbnail(p.getThumbnail())
                .unitPrice(p.getUnitPrice())
                .qty(p.getQty())
                .isAvailable(p.getIsAvailable())
                .isDelete(p.getIsDelete())
                .categoryId(p.getCategory().getId())
                .build()).toList();
    }

    @Override
    public Page<ProductResponse> dynamicProductSearch(List<SearchRequestDto> searchRequestDto, Pageable pageable, RequestDto.GlobalOperator globalOperator){
        Specification<Product> specs = Specification.where(filterSpecification.getSearchSpecificationDynamic(searchRequestDto, globalOperator));
        return productRepo.findAll(specs, pageable).map(productMapper::mapProductToProductResponse);
    }
}
