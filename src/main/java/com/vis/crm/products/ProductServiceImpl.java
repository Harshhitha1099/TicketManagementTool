package com.vis.crm.products;

import com.vis.crm.auditing.AuditResponseDTO;
import com.vis.crm.exception.NotFoundException;
import com.vis.crm.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private  ProductRepository productRepository;


    @Override
    public List<ProductResponseDTO> findAll() {
        List<Product> productDetailsList = productRepository.findAll();
        return productDetailsList.stream()
                .map(this::convertToProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToProductResponseDTO)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product product = convertDTOToProductDetails(productRequestDTO);
        Product savedProduct = productRepository.save(product);
        ProductResponseDTO productResponseDTO = convertToProductResponseDTO(savedProduct);
        return  productResponseDTO;
    }

    @Override
    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete product with id: " + id, e);
        }
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {

        Product existingproduct= productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

        existingproduct.setCategory(productRequestDTO.getCategory());
        existingproduct.setDescription(productRequestDTO.getDescription());
        existingproduct.setName(productRequestDTO.getName());
        existingproduct.setPrice(productRequestDTO.getPrice());
        existingproduct.setStockQuantity(productRequestDTO.getStockQuantity());

        Product savedProduct = productRepository.save(existingproduct);

        ProductResponseDTO productResponseDTO =convertToProductResponseDTO(savedProduct);

        return  productResponseDTO;
    }

    private Product convertDTOToProductDetails(ProductRequestDTO productRequestDTO) {

        Product product = new Product();

        product.setName(productRequestDTO.getName());
        product.setStockQuantity(productRequestDTO.getStockQuantity());
        product.setPrice(productRequestDTO.getPrice());
        product.setDescription(productRequestDTO.getDescription());
        product.setCategory(productRequestDTO.getCategory());

        return product;
    }

    public ProductResponseDTO convertToProductResponseDTO(Product product) {

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setCategory(product.getCategory());
        productResponseDTO.setCreatedAt(product.getCreatedDate());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setModifiedAt(product.getModifiedDate());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setStockQuantity(product.getStockQuantity());
        productResponseDTO.setProductId(product.getProductId());


        if (product.getCreatedBy() != null) {
            productResponseDTO.setCreatedBy(convertToUserModifiedAndCreated(product.getCreatedBy()));
        }

        if (product.getModifiedBy() != null) {
            productResponseDTO.setModifiedBy(convertToUserModifiedAndCreated(product.getModifiedBy()));
        }

        return productResponseDTO;
    }

    private AuditResponseDTO convertToUserModifiedAndCreated(UserDetails userDetails) {
        AuditResponseDTO dto = new AuditResponseDTO();
        dto.setId(userDetails.getId());
        dto.setUsername(userDetails.getUsername());
        return dto;
    }

}



