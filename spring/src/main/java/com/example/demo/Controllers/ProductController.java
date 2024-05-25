package com.example.demo.Controllers;

import com.example.demo.Entity.Coupon;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Users;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600)
public class ProductController {

    private final ProductService productService;

    @Autowired
    private UserService userService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.findAllProducts();
        System.out.println(products.toString());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/service")
    public ResponseEntity<List<Product>> getProductsToService(@RequestHeader("Authorization") String token){
        Users user = userService.getCurrentUser(token.substring(7)); // authorization - bearer
        List<Product> products = productService.findProductByMerchantId(user.getId());
        System.out.println("bu satırdan sonraki merchanta aittir");
        System.out.println(products.toString());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping("/service/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, @RequestHeader("Authorization") String token){
        Users user = userService.getCurrentUser(token.substring(7));
        Product newProduct = productService.addProduct(product);
        newProduct.setMerchantId(user.getId());
        System.out.println("post req tamamdır");
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/service/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product,@RequestHeader("Authorization") String token){
        Users user = userService.getCurrentUser(token.substring(7));
        product.setMerchantId(user.getId());
        Product updateProduct = productService.updateProduct(product);
        updateProduct.setMerchantId(user.getId());
        System.out.println("update tamamdır");
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/service/delete/{id}")
    public ResponseEntity<?> deleteProduct( @PathVariable("id") Long id  ){

        System.out.println("delete içerisinden");
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductBYId(@PathVariable("id") Long id){
        Product product = productService.findProductById(id);

        System.out.println(product.toString());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/products/recommended")
    public ResponseEntity<List<Product>> getRecommendedProducts(@RequestHeader("Authorization") String token){
        Users user = userService.getCurrentUser(token.substring(7));
        Long[] category = user.getInterestedCategory();
        List<Product> products = productService.findAllProducts();
        if (category.length > 0) {


            List<Product> recommendedProducts = new ArrayList<>();
            int count = 0;
            for (Long i :category){
                for (Product p :products){
                    if (p.getCategoryId()==i){
                        recommendedProducts.add(p);
                        count++;
                    }
                    else {}
                }
            }
            return new ResponseEntity<>(recommendedProducts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping("/products/recommendedtokenless")
    public ResponseEntity<List<Product>> getRecommendedProductsWithoutToken(){
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);

    }

}
