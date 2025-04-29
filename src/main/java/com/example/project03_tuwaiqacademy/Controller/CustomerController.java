package com.example.project03_tuwaiqacademy.Controller;

import com.example.project03_tuwaiqacademy.Api.ApiResponse;
import com.example.project03_tuwaiqacademy.DTO.CustomerDTO;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //authority -> EMPLOYEE
    @GetMapping("/get-customers")
    public ResponseEntity getAllCustomers(@AuthenticationPrincipal User employee) {
        return ResponseEntity.status(200).body(customerService.getAllCustomers(employee.getId()));
    }

    //PERMIT ALL
    @PostMapping("/register-customer")
    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        customerService.registerCustomer(customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("new customer registered"));
    }

    //authority -> CUSTOMER
    @PutMapping("/update-customer")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User customer, @RequestBody @Valid CustomerDTO customerDTO) {
        customerService.updateCustomer(customer.getId(), customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("customer updated"));
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user) {
        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("your account is deleted"));
    }


}
