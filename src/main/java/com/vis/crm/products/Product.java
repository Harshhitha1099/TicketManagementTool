package com.vis.crm.products;

import com.vis.crm.auditing.AuditMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends AuditMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull(message = "name is required")
    private String name;

    private String description;

    @NotNull(message = "category is required")
    private String category;

    @NotNull(message = "price is required")
    private BigDecimal price;

    @NotNull(message = "stockQuantity is required")
    private Integer stockQuantity;

}
