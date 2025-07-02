package com.princez1.ZShop.service;

import com.princez1.ZShop.dtos.ProductDTO;
import com.princez1.ZShop.dtos.ProductImageDTO;
import com.princez1.ZShop.entities.Product;
import com.princez1.ZShop.entities.ProductImage;
import com.princez1.ZShop.responses.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id) throws Exception;

    List<Product> findProductsByIds(List<Long> productIds);

    public Page<ProductResponse> getAllProducts(String keyword,
                                                Long categoryId, PageRequest pageRequest);
    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;
    String storeFile(MultipartFile file) throws IOException;
    void deleteFile(String filename) throws IOException;
}
