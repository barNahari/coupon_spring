package com.example.coupondb.controllers;

import com.example.coupondb.beans.Company;
import com.example.coupondb.beans.Customer;
import com.example.coupondb.beans.UserDetails;
import com.example.coupondb.enums.ClientType;
import com.example.coupondb.exceptions.*;
import com.example.coupondb.service.AdministratorService;
import com.example.coupondb.service.ClientService;
import com.example.coupondb.service.LoginManager;
import com.example.coupondb.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("administrator")
public class AdministartorController {
    private final AdministratorService administratorService;

    private ClientService clientService;
    private final JWTutil jwTutil;
    /**
     * @param userDetails get the all user details (email,password)
     * @return validation that they are correct
     */
    @SneakyThrows
    @PostMapping("adminLogin")
    public ResponseEntity<?> adminLogin(@RequestBody UserDetails userDetails) throws AdministratorException {
        try {
            clientService =  new LoginManager().login(userDetails.getEmail(), userDetails.getPassword(), ClientType.Administartor);
            userDetails.setUserID(0); //setting id
            String myToken = jwTutil.generateToken(userDetails);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("Login OK");

        } catch (UserException | CompanyException | CustomerException e) {
            throw new AdministratorException("invalid user name or password");

        }
    }

    /**
     * @param companyid get company id
     * @return get all company details
     */
    @SneakyThrows
    @GetMapping("getOneCompany/{companyid}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name="Authorization") String myToken, @PathVariable int companyid) throws CompanyException {
        try {
            //return new ResponseEntity<>(administratorService.getCompanyById(companyid), HttpStatus.OK);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body(administratorService.getCompanyById(companyid));
        } catch (CompanyException e) {
          throw new CompanyException("company dose not exists");

        }
    }

    /**
     * @param id get customer id
     * @return customer details
     */
    @SneakyThrows
    @GetMapping("getOneCustomer/{id}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name="Authorization") String myToken,@PathVariable int id) throws CustomerException {
        try {
            //return new ResponseEntity<>(administratorService.getCustomerById(id), HttpStatus.OK);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body(administratorService.getCustomerById(id));
        } catch (CustomerException e) {
            throw new CustomerException("customer dose not exists");

        }
    }

    /**
     * @return all companies in the DB
     */
    @SneakyThrows
    @PostMapping("getAllCompanies") //http://localhost:8080/school/add
    public ResponseEntity<?> getALLCompanies(@RequestHeader(name="Authorization") String myToken) {
        //return new ResponseEntity<>(administratorService.getAllCompanies(), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(administratorService.getAllCompanies());

    }

    /**
     * @return all customers in the DB
     */
    @SneakyThrows
    @PostMapping("getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name="Authorization") String myToken) {
        //return new ResponseEntity<>(administratorService.getAllCustomers(), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(administratorService.getAllCustomers());
    }

    /**
     * @param company inject a company
     * @return validation that it went through
     * @throws CompanyException if something went wrong
     */
    @SneakyThrows
    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name="Authorization") String myToken,@RequestBody Company company) throws CompanyException {
        try {
            administratorService.addCompany(company);
            //return new ResponseEntity<>(HttpStatus.CREATED);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("company was add");
        } catch (CompanyException e) {
           throw new CompanyException("cannot add company");
        }

    }

    /**
     * @param customer inject a customer
     * @return validation that it went through
     * @throws CustomerException if something went wrong
     */
    @SneakyThrows
    @PostMapping("addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name="Authorization") String myToken,@RequestBody Customer customer) throws CustomerException {
        administratorService.addCustomer(customer);
        //return new ResponseEntity<>(HttpStatus.CREATED);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body("customer was add");
    }

    /**
     * @param company update a company
     * @return validation that it worked
     */
    @SneakyThrows
    @PostMapping("updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name="Authorization") String myToken,@RequestBody Company company) throws CompanyException {
        try {
            administratorService.updateCompany(company);
            //return new ResponseEntity<>(HttpStatus.ACCEPTED);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("company was updated");
        } catch (CompanyException err) {
            throw new CompanyException("company does not exists");
        }
    }

    /**
     * @param customer update a customer
     * @return validation that it worked
     */
    @SneakyThrows
    @PostMapping("updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name="Authorization") String myToken,@RequestBody Customer customer) throws CustomerException {
        try {
            administratorService.updateCustomer(customer);
            //return new ResponseEntity<>(HttpStatus.ACCEPTED);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("customer was updated");
        } catch (CustomerException err) {
            throw new CustomerException("customer does not exists");

        }

    }

    /**
     * @param companyid get company id
     * @return validation that the company was deleted
     */
    @SneakyThrows
    @GetMapping("deleteCompany/{companyid}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name="Authorization") String myToken,@PathVariable int companyid) throws CompanyException {
        try {
            administratorService.deleteCompany(companyid);
            //return new ResponseEntity<>(HttpStatus.ACCEPTED);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("company was deleted");
        } catch (CompanyException e) {
            throw new CompanyException("company id is not valid");
        }

    }

    /**
     * @param id get customer id
     * @return validation that the customer was deleted
     */
    @SneakyThrows
    @GetMapping("deleteCustomer/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name="Authorization") String myToken,@PathVariable int id) throws CustomerException {
        try {
            administratorService.deleteCustomer(id);
            //return new ResponseEntity<>(HttpStatus.ACCEPTED);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("customer was deleted");
        } catch (CustomerException e) {
            throw new CustomerException("customer id is not valid");
        }

    }
    @SneakyThrows
    @GetMapping("getOneCoupon/{id}")
    public ResponseEntity<?> getOneCoupon(@RequestHeader(name="Authorization") String myToken,@PathVariable int id) throws CouponException {
        try {
            //return new ResponseEntity<>(administratorService.getCouponById(id), HttpStatus.OK);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body(administratorService.getCouponById(id));
        } catch (CouponException e) {
            throw new CouponException("coupon dose not exists");

        }
    }


}
