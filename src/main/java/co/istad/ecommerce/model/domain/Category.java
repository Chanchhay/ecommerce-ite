package co.istad.ecommerce.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

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
