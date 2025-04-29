package com.example.project03_tuwaiqacademy.Service;

import com.example.project03_tuwaiqacademy.Api.ApiException;
import com.example.project03_tuwaiqacademy.DTO.CustomerDTO;
import com.example.project03_tuwaiqacademy.Model.Account;
import com.example.project03_tuwaiqacademy.Model.Customer;
import com.example.project03_tuwaiqacademy.Model.Employee;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Repository.AccountRepository;
import com.example.project03_tuwaiqacademy.Repository.AuthRepository;
import com.example.project03_tuwaiqacademy.Repository.CustomerRepository;
import com.example.project03_tuwaiqacademy.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;
    private final AccountRepository accountRepository;

    //authority -> EMPLOYEE
    public List<Customer> getAllCustomers(Integer employee_id) {
        Employee employee = employeeRepository.findEmployeeById(employee_id);
        if (employee == null)
            throw new ApiException("no permission only for employees");
        return customerRepository.findAll();
    }

    //PERMIT ALL
    public void registerCustomer(CustomerDTO customerDTO) {
        User user = new User();
        user.setRole("CUSTOMER");
        user.setName(customerDTO.getName());
        String hashPassword = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hashPassword);
        user.setEmail(customerDTO.getEmail());
        user.setUsername(customerDTO.getUsername());

        Customer customer = new Customer(null, customerDTO.getPhone_number(), user, null);
        user.setCustomer(customer);
        customerRepository.save(customer);
        authRepository.save(user);
    }

    //authority -> CUSTOMER
    public void updateCustomer(Integer customer_id, CustomerDTO customerDTO) {
        User user = authRepository.findUserById(customer_id);
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (user == null)
            throw new ApiException("customer not found");
        user.setName(customerDTO.getName());
        user.setUsername(customerDTO.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hashPassword);
        user.setEmail(customerDTO.getEmail());
        customer.setPhone_number(customerDTO.getPhone_number());

        customerRepository.save(customer);
        authRepository.save(user);
    }

    public void deleteCustomer(Integer customer_id) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        List<Account> accounts = accountRepository.findMyAccounts(customer_id);
        accountRepository.deleteAll(accounts);
        customerRepository.delete(customer);
    }


}
