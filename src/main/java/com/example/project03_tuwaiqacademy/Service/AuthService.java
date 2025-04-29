package com.example.project03_tuwaiqacademy.Service;

import com.example.project03_tuwaiqacademy.Api.ApiException;
import com.example.project03_tuwaiqacademy.DTO.CustomerDTO;
import com.example.project03_tuwaiqacademy.DTO.EmployeeDTO;
import com.example.project03_tuwaiqacademy.Model.Customer;
import com.example.project03_tuwaiqacademy.Model.Employee;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        user.setRole("ADMIN");

        String hashPassword = new BCryptPasswordEncoder().encode("12345678");
        user.setPassword(hashPassword);

        authRepository.save(user);
    }

    //authority -> ADMIN
    public List<User> getAll(Integer admin_id){
        User admin = authRepository.findUserById(admin_id);
        if (admin==null)
            throw new ApiException("you don't have permission");
        return authRepository.findAll();
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
