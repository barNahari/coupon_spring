package com.example.coupondb.service;

import com.example.coupondb.beans.Company;
import com.example.coupondb.enums.Category;
import com.example.coupondb.beans.Coupon;
import com.example.coupondb.exceptions.CompanyException;
import com.example.coupondb.exceptions.CouponException;
import com.example.coupondb.repositories.CompanyRepo;
import com.example.coupondb.repositories.CouponRepo;
import com.example.coupondb.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service

public class CompanyService extends ClientService {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private CustomerRepo customerRepo;

    private int companyid;

    /**
     * @param email    get company email
     * @param password get company password
     * @return validation that the details ar correct
     * @throws CompanyException if email or password are not valid
     */
    public boolean login(String email, String password) throws CompanyException {
        if (companyRepo.existsCompanyByEmailAndPassword(email, password)) {
            companyid = companyRepo.findByEmailAndPassword(email, password).getCompanyid();
            return true;
        } else {
            throw new CompanyException("email or password are not valid");
        }
    }

    /**
     * @param coupon get a coupon
     * @throws CouponException if something was wrong
     */
    public void addCoupon(Coupon coupon) throws CouponException {
        if (!couponRepo.existsById(coupon.getId())) {
            couponRepo.save(coupon);
        } else {
            throw new CouponException("Coupon is already exists");
        }
    }

    /**
     * @param coupon get a coupon
     * @throws CouponException if something was wrong
     */
    public void updateCoupon(Coupon coupon) throws CouponException {
        if (couponRepo.existsById(coupon.getId())) {
            couponRepo.saveAndFlush(coupon);
        } else {
            throw new CouponException("Coupon dose not exists");
        }
    }

    /**
     * @param id get coupon id
     * @throws CouponException if something was wrong
     */
    public void deleteCoupon(int id) throws CouponException {
        if (couponRepo.existsById(id)) {
            couponRepo.deleteById(id);
        } else {
            throw new CouponException("Coupon dose not exists");
        }
    }

    /**
     * @param id get company id
     * @return list of the company coupons
     */
    public List<Coupon> getAllCouponsByCompany(int id) {
        return companyRepo.findById(id).getCoupons();
    }

    /**
     * @param companyid get company id
     * @param category  get coupons category
     * @return list of coupons
     */
    public List<Coupon> getAllCouponsByCompanyAndCategory(int companyid, Category category) {
        return couponRepo.findBycompanyidAndCategory(companyid, category);
    }

    /**
     * @param id    get company id
     * @param price get the max price
     * @return list of company coupons up to the max price
     */
    public List<Coupon> getAllCompanyCouponsUpToPrice(int id, double price) {
        List<Coupon> coupons = companyRepo.getOne(id).getCoupons();
        List<Coupon> coupons1 = new ArrayList<>();
        for (Coupon item : coupons) {
            if (item.getPrice() <= price) {
                coupons1.add(item);
            }

        }
        return coupons1;

    }

    /**
     * @param companyid get company id
     * @return company details
     */
    //todo
    public Company getCompanyDetail(int companyid) {
        return companyRepo.getOne(companyid);
    }

}