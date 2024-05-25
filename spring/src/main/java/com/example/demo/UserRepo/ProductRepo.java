package com.example.demo.UserRepo;

import com.example.demo.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findProductByMerchantId(Long merchantId);

    void deleteProductById(Long id);

    Product findProductById(Long id);

    List<Product> findByCategoryId(Long categoryId);
}
