package com.vis.crm.products;

import java.util.List;


public interface ProductService {

    List<ProductResponseDTO> findAll();

    ProductResponseDTO findById(Long id);
    ProductResponseDTO save(ProductRequestDTO productRequestDTO);
    void deleteById(Long id);
    ProductResponseDTO updateProduct(Long id,ProductRequestDTO productRequestDTO);
}
