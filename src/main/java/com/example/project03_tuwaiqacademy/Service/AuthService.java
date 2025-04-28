package com.example.project03_tuwaiqacademy.Service;

import com.example.project03_tuwaiqacademy.Api.ApiException;
import com.example.project03_tuwaiqacademy.DTO.CustomerDTO;
import com.example.project03_tuwaiqacademy.DTO.EmployeeDTO;
import com.example.project03_tuwaiqacademy.Model.Customer;
import com.example.project03_tuwaiqacademy.Model.Employee;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;


    //PERMIT ALL
    public void assignAdmin(){
        User assignedAdmin = authRepository.findUserByUsername("ali2025");
        if (assignedAdmin!=null)
            throw new ApiException("admin is already assigned!!");
        User user = new User();
        user.setName("Ali");
        user.setUsername("ali2025");
        user.setEmail("ali.alshehri.p@gmail.com");
        user.setPassword("12345678");
        user.setRole("ADMIN");
        authRepository.save(user);
    }

    //authority -> EMPLOYEE
    public List<User> getAllCustomers(Integer employee_id){
        User employee = authRepository.findUserById(employee_id);
        if (employee==null)
            throw new ApiException("no permission only for employees");
        return authRepository.findCustomersByEmployee(employee_id);
    }

    //authority -> ADMIN
    public List<User> getAll(Integer admin_id){
        User admin = authRepository.findUserById(admin_id);
        if (admin==null)
            throw new ApiException("you don't have permission");
        return authRepository.findAll();
    }

    //PERMIT ALL
    public void registerCustomer(CustomerDTO customerDTO){
        User user = new User();
        user.setRole("CUSTOMER");
        user.setName(customerDTO.getName());
        user.setPassword(customerDTO.getPassword());
        user.setEmail(customerDTO.getEmail());
        user.setUsername(customerDTO.getUsername());

        Customer customer = new Customer(null, customerDTO.getPhone_number(), user,null);
        user.setCustomer(customer);
        authRepository.save(user);
    }

    //PERMIT ALL
    public void registerEmployee(EmployeeDTO employeeDTO){
        User user = new User();
        user.setRole("EMPLOYEE");
        user.setName(employeeDTO.getName());
        user.setPassword(employeeDTO.getPassword());
        user.setEmail(employeeDTO.getEmail());
        user.setUsername(employeeDTO.getUsername());

        Employee employee = new Employee(null, employeeDTO.getPosition(), employeeDTO.getSalary(),user);
        user.setEmployee(employee);
        authRepository.save(user);
    }

    //authority -> CUSTOMER
    public void updateCustomer(Integer customer_id, CustomerDTO customerDTO){
        User customer = authRepository.findUserById(customer_id);
        if (customer==null)
            throw new ApiException("customer not found");
        customer.setName(customerDTO.getName());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setEmail(customerDTO.getEmail());
        customer.getCustomer().setPhone_number(customerDTO.getPhone_number());

        authRepository.save(customer);
    }

    //authority -> EMPLOYEE
    public void updateEmployee(Integer employee_id, EmployeeDTO employeeDTO){
        User employee = authRepository.findUserById(employee_id);
        if (employee==null)
            throw new ApiException("Employee not found");
        employee.setName(employeeDTO.getName());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setEmail(employeeDTO.getEmail());
        employee.getEmployee().setPosition(employeeDTO.getPosition());
    }

    //authority -> ADMIN
    public void deleteUser(Integer admin_id,Integer user_id){
        User admin = authRepository.findUserById(admin_id);
        if (admin==null)
            throw new ApiException("no permission");
        User user = authRepository.findUserById(user_id);
        if (user==null)
            throw new ApiException("user not found");

        authRepository.delete(user);
    }



}
