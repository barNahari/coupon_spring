package com.example.coupondb.service;

import com.example.coupondb.exceptions.CompanyException;
import com.example.coupondb.exceptions.CustomerException;
import com.example.coupondb.exceptions.UserException;

public abstract class ClientService {
    public abstract boolean login(String email,String password) throws UserException, CompanyException, CustomerException;

}
