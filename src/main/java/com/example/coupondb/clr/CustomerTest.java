package com.example.coupondb.clr;

import com.example.coupondb.beans.Coupon;
import com.example.coupondb.enums.Category;
import com.example.coupondb.enums.ClientType;
import com.example.coupondb.exceptions.UserException;
import com.example.coupondb.repositories.CompanyRepo;
import com.example.coupondb.repositories.CustomerRepo;
import com.example.coupondb.service.ClientService;
import com.example.coupondb.service.CustomerService;
import com.example.coupondb.service.LoginManager;
import com.example.coupondb.utils.Art;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Order(3)
@Component
@RequiredArgsConstructor
public class CustomerTest implements CommandLineRunner {
    private final LoginManager loginManager;

    ClientService clientService;

    private final CustomerService customerService;

    private final CompanyRepo companyRepo;

    private final CustomerRepo customerRepo;

    private int customerid;
    @Override
    public void run(String... args) throws Exception {

        System.out.println("==============================================================================");
        System.out.println(Art.customer);
        try {
            clientService = loginManager.login("barNahari@gmail.com","bar12345",ClientType.Customer);
            customerid = customerRepo.findByEmailAndPassword("barNahari@gmail.com","bar12345").getId();
            System.out.println("Welcome customer "+customerRepo.findById(customerid).getFirst_name()+" "+customerRepo.findById(customerid).getLast_name());
        } catch (UserException e) {
            System.out.println(e.getMessage());
            return;
        }
        Coupon coupon1 = Coupon.builder()
                .id(1)
                .companyid(1)
                .category(Category.Sports)
                .amount(2)
                .description("shoes")
                .endDate(Date.valueOf("2022-08-31"))
                .startDate(Date.valueOf(LocalDate.now()))
                .image("")
                .price(300)
                .Title("white SB")
                .build();
        Coupon coupon2 = Coupon.builder()
                .id(2)
                .companyid(1)
                .category(Category.Sports)
                .amount(7)
                .description("shoes")
                .endDate(Date.valueOf("2022-08-31"))
                .startDate(Date.valueOf(LocalDate.now()))
                .image("")
                .price(300)
                .Title("black SB")
                .build();
        System.out.println("ADD COUPON PURCHASE");
        customerService.purchaseCoupon(coupon1);
        customerService.purchaseCoupon(coupon2);
        System.out.println(customerid);
        System.out.println("GET ALL CUSTOMER COUPONS");
        System.out.println(customerService.getAllCustomerCoupons(customerid));
        System.out.println("GET ALL CUSTOMER COUPONS BY CATEGORY");
        System.out.println(customerService.getAllCouponsByCompanyAndCategory(customerid, Category.Sports));
        System.out.println("GET ALL CUSTOMER COUPONS UP TO PRICE");
        System.out.println(customerService.getAllCustomerCouponsUpTOPrice(customerid,350));
        System.out.println("GET CUSTOMER DETAILS");
        System.out.println(customerRepo.findById(customerid));
    }


}
