package co.istad.ecommerce.features.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String customerId;

    @Column
    private String address;

    @Column
    private Boolean status;

    @Column
    private Double discount;

    @Column
    private String remark;

    @Column
    private LocalDateTime orderedAt;

    @Column
    private Boolean isDelete;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;
}
