package com.example.project03_tuwaiqacademy.Repository;

import com.example.project03_tuwaiqacademy.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findCustomerById(Integer id);

}
