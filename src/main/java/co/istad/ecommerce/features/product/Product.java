package co.istad.ecommerce.features.product;

import co.istad.ecommerce.features.category.Category;
import co.istad.ecommerce.features.order.OrderLine;
import co.istad.ecommerce.features.product.dto.ProductRequest;
import co.istad.ecommerce.features.product.dto.ProductResponse;
import co.istad.ecommerce.config.filter.RequestDto;
import co.istad.ecommerce.config.filter.SearchRequestDto;
import co.istad.ecommerce.config.specification.FilterSpecification;
import jakarta.persistence.*;
import lombok.*;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false, length = 20)
    private String code;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private String description;

    @Column
    private String thumbnail;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Boolean isAvailable;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
//    @JoinColumn(name = "category_id")
//    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;
}