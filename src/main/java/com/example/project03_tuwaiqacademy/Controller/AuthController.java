package com.example.project03_tuwaiqacademy.Controller;

import com.example.project03_tuwaiqacademy.Api.ApiResponse;
import com.example.project03_tuwaiqacademy.DTO.CustomerDTO;
import com.example.project03_tuwaiqacademy.DTO.EmployeeDTO;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    //authority -> ADMIN
    @GetMapping("/get-all")
    public ResponseEntity getAll(@AuthenticationPrincipal User admin){
        return ResponseEntity.status(200).body(authService.getAll(admin.getId()));
    }

    //authority -> ADMIN
    @DeleteMapping("/delete-user/user/{user_id}")
    public ResponseEntity deleteUser(@AuthenticationPrincipal User admin,@PathVariable Integer user_id){
        authService.deleteUser(admin.getId(), user_id);
        return ResponseEntity.status(200).body(new ApiResponse("user deleted"));
    }

    //LOGOUT
    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(200).body(new ApiResponse("logout successfully"));
    }



}
