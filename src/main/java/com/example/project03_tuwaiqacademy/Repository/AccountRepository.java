package com.example.project03_tuwaiqacademy.Repository;

import com.example.project03_tuwaiqacademy.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    Account findAccountById(Integer id);

    @Query("select a from Account a where a.customer.id=?1")
    List<Account> findMyAccounts(Integer customer_id);
}
