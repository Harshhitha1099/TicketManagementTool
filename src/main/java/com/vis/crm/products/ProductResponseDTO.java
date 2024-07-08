package com.vis.crm.products;

import com.vis.crm.auditing.AuditResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Long productId;

    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    private Integer stockQuantity;

    private AuditResponseDTO createdBy;

    private AuditResponseDTO modifiedBy;

    private Date modifiedAt;

    private Date createdAt;
}
