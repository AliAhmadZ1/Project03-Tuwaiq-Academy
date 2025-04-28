package com.example.project03_tuwaiqacademy.Controller;

import com.example.project03_tuwaiqacademy.Api.ApiResponse;
import com.example.project03_tuwaiqacademy.DTO.CustomerDTO;
import com.example.project03_tuwaiqacademy.DTO.EmployeeDTO;
import com.example.project03_tuwaiqacademy.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //PERMIT ALL
    @PostMapping("/assign-admin")
    public ResponseEntity assignAdmin(){
        authService.assignAdmin();
        return ResponseEntity.status(200).body(new ApiResponse("admin assigned"));
    }

    //authority -> EMPLOYEE
    @GetMapping("/get-customers/{employee_id}")
    public ResponseEntity getAllCustomers(@PathVariable Integer employee_id){
        return ResponseEntity.status(200).body(authService.getAllCustomers(employee_id));
    }

    //authority -> ADMIN
    @GetMapping("/get-all/{admin_id}")
    public ResponseEntity getAll(@PathVariable Integer admin_id){
        return ResponseEntity.status(200).body(authService.getAll(admin_id));
    }

    //PERMIT ALL
    @PostMapping("/register-customer")
    public ResponseEntity registerCustomer(@RequestBody@Valid CustomerDTO customerDTO){
        authService.registerCustomer(customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("new customer registered"));
    }

    //PERMIT ALL
    @PostMapping("/register-employee")
    public ResponseEntity registerEmployee(@RequestBody@Valid EmployeeDTO employeeDTO){
        authService.registerEmployee(employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("new employee registered"));
    }

    //authority -> CUSTOMER
    @PutMapping("/update-customer/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id,@RequestBody@Valid CustomerDTO customerDTO){
        authService.updateCustomer(id, customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("customer updated"));
    }

    //authority -> EMPLOYEE
    @PutMapping("/update-employee/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id,@RequestBody@Valid EmployeeDTO employeeDTO){
        authService.updateEmployee(id, employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("customer updated"));
    }

    //authority -> ADMIN
    @DeleteMapping("/delete-user/{admin_id}/user/{user_id}")
    public ResponseEntity deleteUser(@PathVariable Integer admin_id,@PathVariable Integer user_id){
        authService.deleteUser(admin_id, user_id);
        return ResponseEntity.status(200).body(new ApiResponse("user deleted"));
    }



}
