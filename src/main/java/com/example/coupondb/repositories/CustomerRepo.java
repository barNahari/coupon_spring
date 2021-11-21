package com.example.coupondb.repositories;


import com.example.coupondb.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Customer findById(int id);

    Customer findByEmailAndPassword(String email, String password);

    boolean existsCustomerByEmailAndPassword(String email, String password);


}
