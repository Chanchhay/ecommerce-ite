package co.istad.ecommerce.features.category;

import co.istad.ecommerce.config.audit.BasedAuditingEntity;
import co.istad.ecommerce.features.category.dto.CategoryRes;
import co.istad.ecommerce.features.category.dto.CreateCategoryReq;
import co.istad.ecommerce.features.product.Product;
import co.istad.ecommerce.config.filter.RequestDto;
import co.istad.ecommerce.config.filter.SearchRequestDto;
import co.istad.ecommerce.features.category.mapper.CategoryMapper;
import co.istad.ecommerce.config.repository.CategorySpecification;
import co.istad.ecommerce.config.specification.FilterSpecification;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BasedAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private String icon;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    private Category parentCategory;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
