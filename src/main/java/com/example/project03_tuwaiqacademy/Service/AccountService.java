package com.example.project03_tuwaiqacademy.Service;

import com.example.project03_tuwaiqacademy.Api.ApiException;
import com.example.project03_tuwaiqacademy.Model.Account;
import com.example.project03_tuwaiqacademy.Model.Customer;
import com.example.project03_tuwaiqacademy.Model.Employee;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Repository.AccountRepository;
import com.example.project03_tuwaiqacademy.Repository.AuthRepository;
import com.example.project03_tuwaiqacademy.Repository.CustomerRepository;
import com.example.project03_tuwaiqacademy.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;


    //authority -> CUSTOMER
    public List<Account> getMyAccounts(Integer customer_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");
        return accountRepository.findMyAccounts(customer_id);
    }


    //authority -> CUSTOMER
    public Account getAccountDetail(Integer customer_id,Integer account_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
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
    public void createAccount(Integer customer_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");

        String generatedAccountNumber = generateAccountNumber();
        Account account = new Account();
        for (Account a:accountRepository.findAll()) {
            if (a.getAccount_number().equals(generatedAccountNumber)) {
                generatedAccountNumber = generateAccountNumber();
                continue;
            }
        }

        account.setAccount_number(generatedAccountNumber);
        account.setBalance(0.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        customerRepository.save(customer);
        accountRepository.save(account);
    }


    //authority -> EMPLOYEE
    public void activateAccount(Integer employee_id,Integer account_id){
        Employee employee = employeeRepository.findEmployeeById(employee_id);
        if (employee==null)
            throw new ApiException("no permission only for employees");
        Account account = accountRepository.findAccountById(account_id);
        if (account==null)
            throw new ApiException("account not found");
        if (account.getIs_active())
            throw new ApiException("is already activated");
        account.setIs_active(true);
        accountRepository.save(account);
    }


    //authority -> EMPLOYEE
    public void blockAccount(Integer employee_id,Integer account_id){
        Employee employee = employeeRepository.findEmployeeById(employee_id);
        if (employee==null)
            throw new ApiException("no permission only for employees");
        Account account = accountRepository.findAccountById(account_id);
        if (account==null)
            throw new ApiException("account not found");
        if (!account.getIs_active())
            throw new ApiException("is already blocked");
        account.setIs_active(false);
        accountRepository.save(account);
    }


    public void deleteAccount(Integer customer_id,Integer account_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        Account account = accountRepository.findAccountById(account_id);
        if (customer==null)
            throw new ApiException("customer not found");
        if (account==null)
            throw new ApiException("account not found");
        if (customer.getId()!=account.getCustomer().getId())
            throw new ApiException("you didn't have permission to delete this account");
        if (account.getBalance()>0)
            throw new ApiException("your account does have money you can't delete it");
        customer.getAccounts().remove(account);
        customerRepository.save(customer);
        accountRepository.delete(account);
    }


    //authority -> CUSTOMER
    public void depositMoney(Integer customer_id,Integer account_id,double amount){
        Customer customer = customerRepository.findCustomerById(customer_id);
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
        Customer customer = customerRepository.findCustomerById(customer_id);
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
        Customer customer = customerRepository.findCustomerById(customer_id);
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



    // generate account number in format: "xxxx-xxxx-xxxx-xxxx"
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
