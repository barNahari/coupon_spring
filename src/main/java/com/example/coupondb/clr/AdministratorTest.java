package com.example.coupondb.clr;

import com.example.coupondb.beans.Company;
import com.example.coupondb.beans.Coupon;
import com.example.coupondb.beans.Customer;
import com.example.coupondb.enums.Category;
import com.example.coupondb.enums.ClientType;
import com.example.coupondb.exceptions.CompanyException;
import com.example.coupondb.exceptions.CouponException;
import com.example.coupondb.exceptions.CustomerException;
import com.example.coupondb.exceptions.UserException;
import com.example.coupondb.repositories.CompanyRepo;
import com.example.coupondb.service.AdministratorService;
import com.example.coupondb.service.ClientService;
import com.example.coupondb.service.LoginManager;
import com.example.coupondb.utils.Art;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

@Order(1)
@Component
@RequiredArgsConstructor
public class AdministratorTest implements CommandLineRunner {
    private final AdministratorService administratorService;
    private final CompanyRepo companyRepo;

    AdministratorService clientService;

    @Override
    public void run(String... args)   {
        System.out.println(Art.administrator);
        try {
             clientService = (AdministratorService) new LoginManager().login("admin@admin.com","admin", ClientType.Administartor);
            System.out.println("welcome Administrator");
        } catch (UserException | CustomerException | CompanyException e) {
            System.out.println("invalid user name or password");
            return;
        }




        try {

            //Administrator
            Company company1 = Company.builder().
                    companyid(1).
                    name("Nike").
                    email("nike@gmail.com").
                    password("nike12345").
                            build();

            Company company2 = Company.builder().
                    companyid(2).
                    name("OutDoor").
                    email("OutDoor@gmail.com").
                    password("OutDoor12345").
                    build();
            Company company3 = Company.builder().
                    companyid(3).
                    name("ElectricHouse").
                    email("ElectricHouse@gmail.com").
                    password("ElectricHouse12345").
                    build();


            System.out.println("ADD COMPANIES");
            administratorService.addCompany(company1);
            administratorService.addCompany(company2);
            administratorService.addCompany(company3);
            System.out.println("companies were added");
            System.out.println("UPDATE COMPANY");
            administratorService.updateCompany(company3 = Company.builder().
                    companyid(3).
                    name("ElectricHouse").
                    email("ElectricHouse@gmail.com").
                    password("ElectricHouse54321").
                    build());
            System.out.println("company was update");
            System.out.println("DELETE COMPANY");
            administratorService.deleteCompany(2);
            System.out.println("company was deleted");
            System.out.println("GET ALL COMPANIES");
            System.out.println("all companies");
            System.out.println(administratorService.getAllCompanies());
            System.out.println("GET ONE COMPANY BY ID");
            System.out.println(administratorService.getCompanyById(1));
            System.out.println("ADD NEW CUSTOMER");
            Customer customer1 = Customer.builder().
                    id(1)
                    .first_name("Jhon")
                    .last_name("Lenon")
                    .email("jhonLenon@gmail.com")
                    .password("jhon12345")
                    .build();
            Customer customer2 = Customer.builder().
                    id(2)
                    .first_name("Bar")
                    .last_name("Nahari")
                    .email("barNahari@gmail.com")
                    .password("bar12345")
                    .build();
            Customer customer3 = Customer.builder().
                    id(3)
                    .first_name("Eli")
                    .last_name("Cohen")
                    .email("eliCohen@gmail.com")
                    .password("eli12345")
                    .build();

            Customer customer4 = Customer.builder().
                    id(4)
                    .first_name("tom")
                    .last_name("jhons")
                    .email("tomJhons@gmail.com")
                    .password("jhonS12345")
                    .build();
            administratorService.addCustomer(customer1);
            administratorService.addCustomer(customer2);
            administratorService.addCustomer(customer3);
            administratorService.addCustomer(customer4);
            System.out.println("customers were added");
            System.out.println("UPDATE CUSTOMER");
            administratorService.updateCustomer(customer3 = Customer.builder().
                    id(3)
                    .first_name("Eli")
                    .last_name("Nisim")
                    .email("eliNisim@gmail.com")
                    .password("eli12345")
                    .build());
            System.out.println("customer was update");
            System.out.println("DELETE CUSTOMER");
            administratorService.deleteCustomer(1);
            System.out.println("customer was deleted");
            System.out.println("GET ALL CUSTOMERS");
            System.out.println(administratorService.getAllCustomers());
            System.out.println("GET ONE CUSTOMER");

            System.out.println(administratorService.getCompanyById(1));
        } catch(CompanyException| CustomerException err){
            System.out.println(err.getMessage());
        }


    }
}
