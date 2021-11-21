package com.example.coupondb.controllers;

import com.example.coupondb.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GuestController {

    @Autowired
    AdministratorService administratorService;

    @GetMapping("getAllCoupons")
    public ResponseEntity<?> getAllCoupons(){
        return new ResponseEntity<>(administratorService.getAllCoupons(), HttpStatus.OK);
    }
}
