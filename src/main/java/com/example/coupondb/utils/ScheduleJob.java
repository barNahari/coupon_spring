package com.example.coupondb.utils;

import com.example.coupondb.beans.Coupon;
import com.example.coupondb.repositories.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableAsync
@EnableScheduling
public class ScheduleJob {
    @Autowired
    CouponRepo couponRepo;
    @Async
    //0 sec, 30 min, 1 hour, dat, month , time zone
    @Scheduled(cron = "0 0 2 * * ?",zone = "Asia/Jerusalem")
    public void eraseCoupon(){
        System.out.println("Daily job - delete expired coupon");
        //insert code here, deleteing coupon

        //JPA dialect
        //get current date
        Date currentDate = new Date();
        List<Coupon> couponDeleteList = couponRepo.findByendDateBefore(currentDate);
        for (Coupon item:couponDeleteList){
            couponRepo.delete(item);
        }
    }
}
