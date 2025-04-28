package com.example.project03_tuwaiqacademy.Service;

import com.example.project03_tuwaiqacademy.Api.ApiException;
import com.example.project03_tuwaiqacademy.Model.Account;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Repository.AccountRepository;
import com.example.project03_tuwaiqacademy.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.repository.core.support.IncompleteRepositoryCompositionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;


    //authority -> CUSTOMER
    public void createAccount(Integer customer_id){
        User customer = authRepository.findUserById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");

        Account account = new Account();
        account.setAccount_number(generateAccountNumber());
        account.setBalance(0.0);
        account.setCustomer(customer.getCustomer());
        authRepository.save(customer);
        accountRepository.save(account);
    }

    //authority -> EMPLOYEE
    public void activateAccount(Integer employee_id,Integer account_id){
        User employee = authRepository.findUserById(employee_id);
        if (employee==null)
            throw new ApiException("no permission only for employees");
        Account account = accountRepository.findAccountById(account_id);
        if (account==null)
            throw new ApiException("account not found");
        account.setIs_active(true);
        accountRepository.save(account);
    }


    //authority -> CUSTOMER
    public Account getAccountDetail(Integer customer_id,Integer account_id){
        User customer = authRepository.findUserById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");
        Account account = accountRepository.findAccountById(account_id);
        if (account==null)
            throw new ApiException("account not found");
        if (account.getCustomer().getId()!=customer_id)
            throw new ApiException("this is not your account");
        return account;
    }

    //authority -> CUSTOMER
    public List<Account> getMyAccounts(Integer customer_id){
        User customer = authRepository.findUserById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");
        return accountRepository.findMyAccounts(customer_id);
    }


    //authority -> CUSTOMER
    public void depositMoney(Integer customer_id,Integer account_id,double amount){
        User customer = authRepository.findUserById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");
        Account account = accountRepository.findAccountById(account_id);
        if (account==null| !account.getIs_active())
            throw new ApiException("account not found or not active");
        if (account.getCustomer().getId()!=customer_id)
            throw new ApiException("this is not your account");
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
    }

    //authority -> CUSTOMER
    public void withdrawMoney(Integer customer_id,Integer account_id,double amount){
        User customer = authRepository.findUserById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");
        Account account = accountRepository.findAccountById(account_id);
        if (account==null| !account.getIs_active())
            throw new ApiException("account not found or not active");
        if (account.getCustomer().getId()!=customer_id)
            throw new ApiException("this is not your account");
        if (account.getBalance()<amount)
            throw new ApiException("you aren't have enough balance");
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
    }


    //authority -> CUSTOMER
    public void transferBetweenAccounts(Integer customer_id,Integer from_account_id,Integer to_account_id, double amount){
        User customer = authRepository.findUserById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");
        Account fromAccount = accountRepository.findAccountById(from_account_id);
        Account toAccount = accountRepository.findAccountById(to_account_id);
        if (fromAccount==null| !fromAccount.getIs_active())
            throw new ApiException("from account not found or not active");
        if (toAccount==null| !toAccount.getIs_active())
            throw new ApiException("to account not found or not active");
        if (fromAccount.getBalance()<amount)
            throw new ApiException("you aren't have enough balance");
        if (fromAccount.getCustomer().getId()!=customer_id)
            throw new ApiException("this is not your account");
        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }



    //authority -> EMPLOYEE
    public void blockAccount(Integer employee_id,Integer account_id){
        User employee = authRepository.findUserById(employee_id);
        if (employee==null)
            throw new ApiException("no permission only for employees");
        Account account = accountRepository.findAccountById(account_id);
        if (account==null)
            throw new ApiException("account not found");
        account.setIs_active(false);
        accountRepository.save(account);
    }

    public String generateAccountNumber(){
        Random random = new Random();
        int p1 = random.nextInt(1000,9999);
        int p2 = random.nextInt(1000,9999);
        int p3 = random.nextInt(1000,9999);
        int p4 = random.nextInt(1000,9999);

        String format= p1+"-"+p2+"-"+p3+"-"+p4;
        return format;
    }

}
