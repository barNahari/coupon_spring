package com.example.coupondb.clr;

import com.example.coupondb.beans.Coupon;
import com.example.coupondb.enums.Category;
import com.example.coupondb.enums.ClientType;
import com.example.coupondb.exceptions.CompanyException;
import com.example.coupondb.exceptions.CouponException;
import com.example.coupondb.exceptions.CustomerException;
import com.example.coupondb.exceptions.UserException;
import com.example.coupondb.repositories.CompanyRepo;
import com.example.coupondb.service.AdministratorService;
import com.example.coupondb.service.ClientService;
import com.example.coupondb.service.CompanyService;
import com.example.coupondb.service.LoginManager;
import com.example.coupondb.utils.Art;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Order(2)
@Component
@RequiredArgsConstructor
public class CompanyTest implements CommandLineRunner {
    private final CompanyService companyService;

    private final LoginManager loginManager;

    ClientService clientService;

    private final CompanyRepo companyRepo;

    private int companyId1;

    private int companyId2;

    @Override
    public void run(String... args) throws CouponException {
        System.out.println("==============================================================================");
        System.out.println(Art.company);
        try {
            clientService = loginManager.login("nike@gmail.com", "nike12345", ClientType.Company);
            companyId1 = companyRepo.findByEmailAndPassword("nike@gmail.com", "nike12345").getCompanyid();
            System.out.println("Welcome company " + companyRepo.findById(companyId1).getName());
        } catch (UserException | CompanyException | CustomerException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            clientService = loginManager.login("nike@gmail.com", "nike12345", ClientType.Company);
            companyId2 = companyRepo.findByEmailAndPassword("ElectricHouse@gmail.com", "ElectricHouse54321").getCompanyid();
            System.out.println("Welcome company " + companyRepo.findById(companyId2).getName());
        } catch (UserException | CompanyException | CustomerException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {

            Coupon coupon1 = Coupon.builder()
                    .id(1)
                    .companyid(companyId1)
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
                    .companyid(companyId1)
                    .category(Category.Sports)
                    .amount(7)
                    .description("shoes")
                    .endDate(Date.valueOf("2022-08-31"))
                    .startDate(Date.valueOf(LocalDate.now()))
                    .image("")
                    .price(300)
                    .Title("black SB")
                    .build();
            Coupon coupon3 = Coupon.builder()
                    .id(3)
                    .companyid(companyId1)
                    .category(Category.Sports)
                    .amount(6)
                    .description("shoes")
                    .endDate(Date.valueOf("2022-08-31"))
                    .startDate(Date.valueOf(LocalDate.now()))
                    .image("")
                    .price(350)
                    .Title("AirMax")
                    .build();
            Coupon coupon4 = Coupon.builder()
                    .id(4)
                    .companyid(companyId2)
                    .category(Category.Electricity)
                    .amount(10)
                    .description("table lamp")
                    .endDate(Date.valueOf("2022-08-31"))
                    .startDate(Date.valueOf(LocalDate.now()))
                    .image("")
                    .price(60)
                    .Title("lamp")
                    .build();
            Coupon coupon5 = Coupon.builder()
                    .id(5)
                    .companyid(companyId2)
                    .category(Category.Electricity)
                    .amount(5)
                    .description("industrial fan")
                    .endDate(Date.valueOf("2022-08-31"))
                    .startDate(Date.valueOf(LocalDate.now()))
                    .image("")
                    .price(895)
                    .Title("fan")
                    .build();
            Coupon coupon6 = Coupon.builder()
                    .id(6)
                    .companyid(companyId2)
                    .category(Category.Food)
                    .amount(4)
                    .description("fuze tea ")
                    .endDate(Date.valueOf("2022-08-31"))
                    .startDate(Date.valueOf(LocalDate.now()))
                    .image("")
                    .price(6)
                    .Title("drinks")
                    .build();
            System.out.println("ADD COUPONS");
            companyService.addCoupon(coupon1);
            companyService.addCoupon(coupon2);
            companyService.addCoupon(coupon3);
            companyService.addCoupon(coupon4);
            companyService.addCoupon(coupon5);
            companyService.addCoupon(coupon6);
            System.out.println("coupons were added");
            System.out.println("UPDATE COUPON");
            companyService.updateCoupon(coupon2 = Coupon.builder()
                    .id(2)
                    .companyid(1)
                    .category(Category.Sports)
                    .amount(5)
                    .description("shoes")
                    .endDate(Date.valueOf(LocalDate.now()))
                    .startDate(Date.valueOf(LocalDate.now()))
                    .image("")
                    .price(300)
                    .Title("black SB")
                    .build());
            System.out.println("coupon was update");
            System.out.println("DELETE COUPON");
            companyService.deleteCoupon(4);
            System.out.println("coupon was deleted");
            System.out.println("GET ALL COUPONS BY COMPANY");
            System.out.println(companyService.getAllCouponsByCompany(1));
            System.out.println("GET ALL COUPONS BY COMPANY AND CATEGORY");
            System.out.println(companyService.getAllCouponsByCompanyAndCategory(1, Category.Sports));
            System.out.println("GET ALL COUPONS BY COMPANY UP TO PRICE");
            System.out.println(companyService.getAllCompanyCouponsUpToPrice(1, 300));
            System.out.println("GET COMPANY DETAILS");
            System.out.println(companyService.getCompanyDetail(companyId1));
            System.out.println(companyId1);
        } catch (CouponException err) {
            throw new CouponException(err.getMessage());
        }

    }

}
