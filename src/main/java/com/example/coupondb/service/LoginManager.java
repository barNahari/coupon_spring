package com.example.coupondb.service;

import com.example.coupondb.enums.ClientType;
import com.example.coupondb.exceptions.CompanyException;
import com.example.coupondb.exceptions.CustomerException;
import com.example.coupondb.exceptions.UserException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LoginManager {

    @Autowired
    CompanyService companyService;
    @Autowired
    CustomerService customerService;


    /**
     * @param email      get email
     * @param password   get password
     * @param clientType get clientType
     * @return validation if the details are correct
     * @throws UserException     if something was wrong
     * @throws CompanyException  if something was wrong
     * @throws CustomerException if something was wrong
     */
    public ClientService login(String email, String password, ClientType clientType) throws UserException, CompanyException, CustomerException {
        switch (clientType) {
            case Administartor:
                AdministratorService administratorService = new AdministratorService();
                if (administratorService.login(email, password)) {
                    return administratorService;
                }
                throw new UserException("invalid user name or password");
            case Company:
                if (companyService.login(email, password)) {
                    return companyService;
                }

                throw new UserException("invalid user name or password");
            case Customer:
                if (customerService.login(email, password)) {
                    return customerService;
                }
                throw new UserException("invalid user name or password");

            default:
                return null;
        }

    }
}
