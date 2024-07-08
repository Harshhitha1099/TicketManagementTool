package com.vis.crm.products;

import com.vis.crm.exception.NotFoundException;
import com.vis.crm.model.SuccessResponse;
import com.vis.crm.model.FailureResponse;
import com.vis.crm.model.AResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<AResponse> getAllProducts() {
        try {
            List<ProductResponseDTO> products = productService.findAll();
            SuccessResponse<List<ProductResponseDTO>> response = new SuccessResponse<>(products, "Products retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to retrieve products");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AResponse> getProductById(@PathVariable Long id) {
        try {
            ProductResponseDTO product = productService.findById(id);
            SuccessResponse<ProductResponseDTO> response = new SuccessResponse<>(product, "Product retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "Product not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AResponse> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        try {
            ProductResponseDTO product = productService.save(productRequestDTO);
            SuccessResponse<ProductResponseDTO> response = new SuccessResponse<>(product, "Product created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to create product");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        try {
            ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
            SuccessResponse<ProductResponseDTO> response = new SuccessResponse<>(updatedProduct, "Product updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "Product not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteById(id);
            SuccessResponse<String> response = new SuccessResponse<>("Product deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            if (ex.getStatus() == HttpStatus.NOT_FOUND) {
                FailureResponse response = new FailureResponse(ex, "Product not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                FailureResponse response = new FailureResponse(ex, "Failed to delete product: " + ex.getMessage());
                return new ResponseEntity<>(response, ex.getStatus());
            }
        } catch (Exception e) {
            FailureResponse response = new FailureResponse(e, "Failed to delete product: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
