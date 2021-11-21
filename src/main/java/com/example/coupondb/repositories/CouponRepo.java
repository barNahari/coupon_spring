package com.example.coupondb.repositories;

import com.example.coupondb.enums.Category;
import com.example.coupondb.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.List;

@Component
public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    Coupon findById(int id);

    List<Coupon> findBycompanyidAndCategory(int id, Category category);

    //find by endDate that is berfore given date..
    List<Coupon> findByendDateBefore(Date expiredDate);
}
