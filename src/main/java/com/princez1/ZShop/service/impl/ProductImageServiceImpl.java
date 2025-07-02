package com.princez1.ZShop.service.impl;

import com.princez1.ZShop.entities.ProductImage;
import com.princez1.ZShop.exceptions.DataNotFoundException;
import com.princez1.ZShop.repositories.ProductImageRepository;
import com.princez1.ZShop.service.ProductImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    @Override
    @Transactional
    public ProductImage deleteProductImage(Long id) throws Exception {
        Optional<ProductImage> productImage = productImageRepository.findById(id);
        if(productImage.isEmpty()) {
            throw new DataNotFoundException(
                    String.format("Cannot find product image with id: %ld", id)
            );
        }
        productImageRepository.deleteById(id);
        return productImage.get();
    }
}
