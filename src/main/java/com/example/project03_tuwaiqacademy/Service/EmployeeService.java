package com.example.project03_tuwaiqacademy.Service;

import com.example.project03_tuwaiqacademy.Api.ApiException;
import com.example.project03_tuwaiqacademy.DTO.EmployeeDTO;
import com.example.project03_tuwaiqacademy.Model.Employee;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Repository.AuthRepository;
import com.example.project03_tuwaiqacademy.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;
    private final AbstractDetectingUrlHandlerMapping abstractDetectingUrlHandlerMapping;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //PERMIT ALL
    public void registerEmployee(EmployeeDTO employeeDTO) {
        User user = new User();
        user.setRole("EMPLOYEE");
        user.setName(employeeDTO.getName());
        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hashPassword);
        user.setEmail(employeeDTO.getEmail());
        user.setUsername(employeeDTO.getUsername());

        Employee employee = new Employee(null, employeeDTO.getPosition(), employeeDTO.getSalary(), user);
        user.setEmployee(employee);
        employeeRepository.save(employee);
        authRepository.save(user);
    }

    //authority -> EMPLOYEE
    public void updateEmployee(Integer employee_id, EmployeeDTO employeeDTO) {
        User user = authRepository.findUserById(employee_id);
        Employee employee = employeeRepository.findEmployeeById(employee_id);
        if (user == null)
            throw new ApiException("Employee not found");
        user.setName(employeeDTO.getName());
        user.setUsername(employeeDTO.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hashPassword);
        user.setEmail(employeeDTO.getEmail());
        employee.setPosition(employeeDTO.getPosition());

        employeeRepository.save(employee);
        authRepository.save(user);
    }

    public void deleteEmployee(Integer employee_id) {
        Employee employee = employeeRepository.findEmployeeById(employee_id);
        employeeRepository.delete(employee);
    }

}
