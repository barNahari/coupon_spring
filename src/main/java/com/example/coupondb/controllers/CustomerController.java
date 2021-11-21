package com.example.coupondb.controllers;

import com.example.coupondb.beans.Coupon;
import com.example.coupondb.beans.UserDetails;
import com.example.coupondb.enums.Category;
import com.example.coupondb.enums.ClientType;
import com.example.coupondb.exceptions.CompanyException;
import com.example.coupondb.exceptions.CustomerException;
import com.example.coupondb.exceptions.UserException;
import com.example.coupondb.service.ClientService;
import com.example.coupondb.service.CustomerService;
import com.example.coupondb.service.LoginManager;
import com.example.coupondb.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;

    private ClientService clientService;
    private final JWTutil jwTutil;

    @Autowired
    private LoginManager loginManager;

    /**
     *
     * @param userDetails get the all user details (email,password)
     * @return validation that they are correct
     * @throws CustomerException
     */
    @PostMapping("customerLogin")
    public ResponseEntity<?> customerLogin(@RequestBody UserDetails userDetails) throws CustomerException {
        try {
            clientService = loginManager.login(userDetails.getEmail(),userDetails.getEmail(), ClientType.Customer);
            String myToken = jwTutil.generateToken(userDetails);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("Login OK");
        } catch (UserException | CompanyException | CustomerException e) {
            throw new CustomerException("invalid user name or password");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomerException("Invalid token");
        }

    }

    /**
     * @param coupon get a coupon
     * @return if customer can purchase it or not
     */
    @PostMapping("addCouponPurchase")
    public ResponseEntity<?> addCouponPurchase(@RequestHeader(name="Authorization") String myToken,@RequestBody Coupon coupon) {
        customerService.purchaseCoupon(coupon);
        //return new ResponseEntity<>(HttpStatus.OK);
        try {
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("coupon was purchase");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @param customerid put customer id in
     * @return list of all customer coupons
     */
    @SneakyThrows
    @GetMapping("getOneCustomerCoupons/{customerid}")//todo change all post whit path to get...
    public ResponseEntity<?> getOneCustomerCoupons(@RequestHeader(name="Authorization") String myToken,@PathVariable int customerid) {
        //return new ResponseEntity<>(customerService.getAllCustomerCoupons(customerid), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(customerService.getAllCustomerCoupons(customerid));
    }

    /**
     * @param customerid put customer id in
     * @param category   put the category of the coupons
     * @return list of all customer coupons by that category
     */
    @SneakyThrows
    @GetMapping("getOneCustomerCouponsByCategory/{customerid}/{category}")
    public ResponseEntity<?> getOneCustomerCouponsByCategory(@RequestHeader(name="Authorization") String myToken,@PathVariable int customerid, Category category) {
        //return new ResponseEntity<>(customerService.getAllCouponsByCompanyAndCategory(customerid, category), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(customerService.getAllCouponsByCompanyAndCategory(customerid, category));
    }

    /**
     * @param customerid put customer id in
     * @param price      put the price limit
     * @return list of all customer coupons up that price
     */
    @SneakyThrows
    @GetMapping("getOneCustomerCouponsUpToPrice/{customerid}/{price}")
    public ResponseEntity<?> getOneCustomerCouponsUpToPrice(@RequestHeader(name="Authorization") String myToken,@PathVariable int customerid, double price) {
        System.out.println(customerid + " maxprice " + price );
        System.out.println(customerService.getAllCustomerCouponsUpTOPrice(customerid, price));
        //return new ResponseEntity<>(customerService.getAllCustomerCouponsUpTOPrice(customerid, price), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(customerService.getAllCustomerCouponsUpTOPrice(customerid, price));
    }
}
