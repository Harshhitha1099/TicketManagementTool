package com.vis.crm.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {


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
