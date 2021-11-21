package com.example.coupondb.service;

import com.example.coupondb.beans.Coupon;
import com.example.coupondb.beans.Customer;
import com.example.coupondb.enums.Category;
import com.example.coupondb.exceptions.CustomerException;
import com.example.coupondb.repositories.CouponRepo;
import com.example.coupondb.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService extends ClientService {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private LoginManager loginManager;
    @Autowired
    private CouponRepo couponRepo;
    private int customerId;

    /**
     * @param coupon get a coupon
     */
    public void purchaseCoupon(Coupon coupon) {
        Customer customer = customerRepo.findById(customerId);
        List<Coupon> customerCoupons = customer.getCoupons();
        Coupon purchase = null;
        try {
            if (coupon == null) {
                throw new CustomerException("coupon does not exists 1");
            }
            if (couponRepo.existsById(coupon.getId())) {
                purchase = couponRepo.findById(coupon.getId());
            } else {
                throw new CustomerException("coupon does not exists 2");
            }
            boolean okay = false;
            if (!purchase.getEndDate().before(Date.valueOf(LocalDate.now())) && purchase.getAmount() > 0) {
                //if there are no other purchases- purchase can't be a duplicate
                if (customerCoupons.isEmpty()) {
                    okay = true;
                }
                for (Coupon coupon1 : customerCoupons) {
                    if ((coupon1.getCompanyid() != purchase.getCompanyid())) {
                        okay = true;
                    } else {
                        if (!coupon1.getTitle().equals(purchase.getTitle())) {
                            okay = true;
                        }
                    }
                }
            }
            if (okay) {
                customerCoupons.add(purchase);
                purchase.setAmount(purchase.getAmount() - 1);
                customer.setCoupons(customerCoupons);
                customerRepo.saveAndFlush(customer);
                couponRepo.saveAndFlush(purchase);
            } else {
                throw new CustomerException("Error purchasing the coupon");
            }
        } catch (CustomerException systemException) {
            System.out.println(systemException.getMessage());
        }
    }

    /**
     * @param email    get customer email
     * @param password get customer password
     * @return validation that the details are correct
     * @throws CustomerException if something was wrong
     */
    public boolean login(String email, String password) throws CustomerException {
        if (customerRepo.existsCustomerByEmailAndPassword(email, password)) {
            customerId = customerRepo.findByEmailAndPassword(email, password).getId();
            return true;
        } else {
            throw new CustomerException("problem was here");
        }
    }

    /**
     * @param id get customer id
     * @return list of customer coupons
     */
    public List<Coupon> getAllCustomerCoupons(int id) {
        return customerRepo.findById(id).getCoupons();
    }

    /**
     * @param id       get customer id
     * @param category get the category of the coupons
     * @return list of coupons that the customer have by the category
     */
    public List<Coupon> getAllCouponsByCompanyAndCategory(int id, Category category) {
        List<Coupon> coupons = getAllCustomerCoupons(id);
        List<Coupon> coupons1 = new ArrayList<>();
        for (Coupon item : coupons) {
            if (item.getCategory() == category) {
                coupons1.add(item);
            }
        }
        return coupons1;
    }

    /**
     * @param id    get customer id
     * @param price get the max price
     * @return list of customer coupons up to price
     */
    public List<Coupon> getAllCustomerCouponsUpTOPrice(int id, double price) {
        List<Coupon> coupons = getAllCustomerCoupons(id);
        List<Coupon> coupons1 = new ArrayList<>();
        for (Coupon item : coupons) {
            if (item.getPrice() < price) {
                coupons1.add(item);
            }
        }
        return coupons1;
    }

    /**
     * get customer details
     */
    void getCustomerDetails() {
        System.out.println(customerRepo.findById(customerId));
    }
}
