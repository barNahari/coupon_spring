package com.example.coupondb.repositories;

import com.example.coupondb.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
@Transactional
public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Company findById(int id);

    Company findByEmailAndPassword(String email, String password);

    boolean existsCompanyByEmailAndPassword(String email, String password);


}
