package com.example.coupondb.service;

import com.example.coupondb.beans.Company;
import com.example.coupondb.beans.Coupon;
import com.example.coupondb.beans.Customer;
import com.example.coupondb.exceptions.CompanyException;
import com.example.coupondb.exceptions.CouponException;
import com.example.coupondb.exceptions.CustomerException;
import com.example.coupondb.repositories.CompanyRepo;
import com.example.coupondb.repositories.CouponRepo;
import com.example.coupondb.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdministratorService extends ClientService {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CouponRepo couponRepo;

    /**
     * @param email    get email
     * @param password get password
     * @return validation that they are both correct
     */
    public boolean login(String email, String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            return true;
        }
        return false;
    }

    /**
     * @param company inject a company
     * @throws CompanyException if something went wrong
     */
    public void addCompany(Company company) throws CompanyException {
        if (!companyRepo.existsById(company.getCompanyid())) {
            companyRepo.save(company);
        } else {
            throw new CompanyException("company already exists");
        }
    }

    /**
     * @param company get a company
     * @throws CompanyException if something went wrong
     */
    public void updateCompany(Company company) throws CompanyException {
        if (companyRepo.existsById(company.getCompanyid())) {
            companyRepo.saveAndFlush(company);
        } else {
            throw new CompanyException("company dose not exists");
        }
    }

    /**
     * @param id get company id
     * @throws CompanyException if something went wrong
     */
    public void deleteCompany(int id) throws CompanyException {
        if (companyRepo.existsById(id)) {
            companyRepo.deleteById(id);
        } else {
            throw new CompanyException("company dose not exists");
        }
    }

    /**
     * @return all the companies in the DB
     */
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    /**
     * @param id get company id
     * @return company details
     * @throws CompanyException if something went wrong
     */
    public Company getCompanyById(int id) throws CompanyException {
        if (companyRepo.existsById(id)) {
            return companyRepo.findById(id);
        } else {
            throw new CompanyException("Company dose not exists");
        }
    }

    /**
     * @param customer inject a customer
     * @throws CustomerException if something went wrong
     */
    public void addCustomer(Customer customer) throws CustomerException {
        if (!customerRepo.existsById(customer.getId())) {
            customerRepo.save(customer);
        } else {
            throw new CustomerException("coupon already exists");
        }
    }

    /**
     * @param customer get a customer
     * @throws CustomerException if something went wrong
     */
    public void updateCustomer(Customer customer) throws CustomerException {
        if (customerRepo.existsById(customer.getId())) {
            customerRepo.save(customer);
        } else {
            throw new CustomerException("customer dose not exists");
        }
    }

    /**
     * @param id get a customer
     * @throws CustomerException if something went wrong
     */
    public void deleteCustomer(int id) throws CustomerException {
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
        } else {
            throw new CustomerException("customer dose not exists");
        }
    }

    /**
     * @return all customers in DB
     */
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    /**
     * @param id get a customer
     * @return customer details
     * @throws CustomerException if something went wrong
     */
    public Customer getCustomerById(int id) throws CustomerException {
        if (customerRepo.existsById(id)) {
            return customerRepo.findById(id);
        } else {
            throw new CustomerException("customer dose not exists");
        }
    }

    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }
    public  Coupon getCouponById(int id) throws CouponException {
        if(couponRepo.existsById(id)){
            return couponRepo.findById(id);
        }else{
            throw new CouponException("coupon dose not exists");
        }
    }

}
