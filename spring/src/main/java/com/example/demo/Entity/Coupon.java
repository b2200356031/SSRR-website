package com.example.demo.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    public void setProduct(Product product) {
        this.product = product;
    }
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id") // Assuming "id" is the primary key of the Product entity
    private Product product;

    private Integer price;
    private Integer discount;
    private Integer avaibleCoupons;
    private String CouponCode;
    private Long[] ownedUsers;

    public Coupon(){}

    public Long getCouponId(){return couponId;}


    public Integer getPrice(){return price;}

    public Product getProduct(){return product;}

    public Integer getAvaibleCoupons(){return avaibleCoupons;}

    public void setAvaibleCoupons(Integer avaibleCoupons) {this.avaibleCoupons=avaibleCoupons;}

    public Long[] getOwnedUsers() {
        return ownedUsers;
    }

    public void setOwnedUsers(Long[] ownedUsers) {
        this.ownedUsers=ownedUsers;
    }



}
