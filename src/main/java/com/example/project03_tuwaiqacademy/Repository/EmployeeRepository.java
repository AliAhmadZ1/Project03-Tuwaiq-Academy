package com.example.project03_tuwaiqacademy.Repository;

import com.example.project03_tuwaiqacademy.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findEmployeeById(Integer id);

}
