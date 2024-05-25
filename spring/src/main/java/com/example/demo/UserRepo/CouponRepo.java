package com.example.demo.UserRepo;

import com.example.demo.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CouponRepo extends JpaRepository<Coupon, Long> {

    List<Coupon> findCouponByProductId(Long productId);
    void deleteCouponByCouponId(Long couponId);
    Coupon findCouponByCouponId(Long couponId);
    void deleteCouponByProductId(Long productId);


}
