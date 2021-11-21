package com.example.coupondb.controllers;


import com.example.coupondb.beans.Coupon;
import com.example.coupondb.beans.UserDetails;
import com.example.coupondb.enums.Category;
import com.example.coupondb.enums.ClientType;
import com.example.coupondb.exceptions.CompanyException;
import com.example.coupondb.exceptions.CouponException;
import com.example.coupondb.exceptions.CustomerException;
import com.example.coupondb.exceptions.UserException;
import com.example.coupondb.service.ClientService;
import com.example.coupondb.service.CompanyService;
import com.example.coupondb.service.LoginManager;
import com.example.coupondb.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;
    @Autowired
    private LoginManager loginManager;
    private final JWTutil jwTutil;
    private ClientService clientService;

    /**
     *
     * @param userDetails get the all user details (email,password)
     * @return validation that they are correct
     * @throws CompanyException
     */
    @SneakyThrows
    @PostMapping("companyLogin")
    public ResponseEntity<?> companyLogin(@RequestBody UserDetails userDetails) throws CompanyException {
        try {

            clientService = loginManager.login(userDetails.getEmail(), userDetails.getPassword(), ClientType.Company);
            String myToken = jwTutil.generateToken(userDetails);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("Login OK");
        } catch (UserException | CompanyException | CustomerException e) {
            throw new CompanyException("invalid user name or password");

        }
    }

    /**
     * @param coupon get a coupon
     * @return validation if the coupon was added
     */
    @SneakyThrows
    @PostMapping("addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name="Authorization") String myToken,@RequestBody Coupon coupon) throws CompanyException {
        try {
            companyService.addCoupon(coupon);
            //return new ResponseEntity<>(HttpStatus.CREATED);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("Coupon was created");
        } catch (CouponException e) {
            throw new CompanyException("coupon already exists");
        }

    }

    /**
     * @param coupon get a coupon
     * @return validation if that update was a success
     */
    @SneakyThrows
    @PostMapping("updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name="Authorization") String myToken,@RequestBody Coupon coupon) throws CompanyException {
        try {
            companyService.updateCoupon(coupon);
            //return new ResponseEntity<>(HttpStatus.ACCEPTED);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("Coupon was updated");
        } catch (CouponException err) {
            throw new CompanyException("coupon does not exists");

        }

    }

    /**
     * @param id get coupon id
     * @return validation if it was deleted
     */
    @SneakyThrows
    @GetMapping("deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name="Authorization") String myToken,@PathVariable int id) throws CompanyException {
        try {
            companyService.deleteCoupon(id);
            //return new ResponseEntity<>(HttpStatus.ACCEPTED);
            return ResponseEntity.ok()
                    .headers(jwTutil.getHeadres(myToken))
                    .body("Coupon was deleted");
        } catch (CouponException e) {
            throw new CompanyException("coupon dose not exists");
        }

    }

    /**
     * @param companyid get company id
     * @return a list of company coupons
     */
    @SneakyThrows
    @GetMapping("getOneCompanyCoupons/{companyid}")
    public ResponseEntity<?> getOneCompanyCoupons(@RequestHeader(name="Authorization") String myToken,@PathVariable int companyid) {
        //return new ResponseEntity<>(companyService.getAllCouponsByCompany(companyid), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(companyService.getAllCouponsByCompany(companyid));
    }

    /**
     * @param companyid get company id
     * @param category  get the category
     * @return list of coupons
     */

    @SneakyThrows
    @GetMapping("getAllCouponsByCompanyAndCategory/{companyid}/{category}")
    public ResponseEntity<?> getAllCouponsByCompanyAndCategory(@RequestHeader(name="Authorization") String myToken,@PathVariable int companyid, @PathVariable Category category) {
        System.out.println("company:" + companyid + " category" + category);
        //return new ResponseEntity<>(companyService.getAllCouponsByCompanyAndCategory(companyid, category), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(companyService.getAllCouponsByCompanyAndCategory(companyid, category));
    }

    /**
     * @param companyid get company id
     * @param price     get the max price
     * @return list of coupons up to the max price
     */

    @SneakyThrows
    @GetMapping("getAllCompanyCouponsUpToPrice/{companyid}/{price}")
    public ResponseEntity<?> getAllCompanyCouponsUpToPrice(@RequestHeader(name="Authorization") String myToken,@PathVariable int companyid,@PathVariable double price) {
        //return new ResponseEntity<>(companyService.getAllCompanyCouponsUpToPrice(companyid, price), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(companyService.getAllCompanyCouponsUpToPrice(companyid, price));
    }

    /**
     * @param companyid get company id
     * @return company details
     */
    @SneakyThrows
    @GetMapping("getOneCompany/{companyid}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name="Authorization") String myToken,@PathVariable int companyid) {
        //return new ResponseEntity<>(companyService.getCompanyDetail(companyid), HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(jwTutil.getHeadres(myToken))
                .body(companyService.getCompanyDetail(companyid));
    }
}
