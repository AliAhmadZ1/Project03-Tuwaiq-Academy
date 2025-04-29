package com.example.project03_tuwaiqacademy.Controller;

import com.example.project03_tuwaiqacademy.Api.ApiResponse;
import com.example.project03_tuwaiqacademy.DTO.EmployeeDTO;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get-all")
    public ResponseEntity getAllEmployees(){
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }
    //PERMIT ALL
    @PostMapping("/register-employee")
    public ResponseEntity registerEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("new employee registered"));
    }

    //authority -> EMPLOYEE
    @PutMapping("/update-employee")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User employee, @RequestBody@Valid EmployeeDTO employeeDTO){
        employeeService.updateEmployee(employee.getId(), employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("employee updated"));
    }

    @DeleteMapping("/delete-employee")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal User user){
        employeeService.deleteEmployee(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("your account is deleted"));
    }
}
