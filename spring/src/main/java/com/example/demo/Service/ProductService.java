package com.example.demo.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.Review;
import com.example.demo.Entity.Users;
import com.example.demo.UserRepo.CouponRepo;
import com.example.demo.UserRepo.ProductRepo;
import com.example.demo.UserRepo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ReviewRepo reviewRepo;
    private final CouponRepo couponRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, ReviewRepo reviewRepo, CouponRepo couponRepo){
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
        this.couponRepo = couponRepo;
    }

    public Product addProduct(Product product){
        product.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        return productRepo.save(product);
    }

    public List<Product> findAllProducts(){
        return productRepo.findAll();
    }

    public List<Product> findProductByMerchantId(Long merchantId){
        return productRepo.findProductByMerchantId(merchantId);
    }

    public Product updateProduct(Product product){
        System.out.println(product.toString());
        return productRepo.save(product);
    }

    public void deleteProduct(Long id){
        couponRepo.deleteCouponByProductId(id);
        reviewRepo.deleteReviewByProductId(id);
        productRepo.deleteProductById(id);
    }

    public Product findProductById(Long id) {
        return productRepo.findProductById(id);
    }


    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepo.findByCategoryId(categoryId);
    }


}
