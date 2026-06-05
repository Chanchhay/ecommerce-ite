package co.istad.ecommerce.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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
    private Boolean isDelete;

    @ManyToOne
//    @JoinColumn(name = "category_id")
//    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;
}