package com.vis.crm.auditing;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditResponseDTO
{
    private Long id;

    private String username;
}
