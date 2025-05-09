package com.example.project03_tuwaiqacademy.Repository;

import com.example.project03_tuwaiqacademy.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    @Query("select c from User c where c.role='CUSTOMER'")
    List<User> findCustomersByEmployee();

    User findUserByUsername(String username);
}
