package com.example.project03_tuwaiqacademy.Controller;

import com.example.project03_tuwaiqacademy.Api.ApiResponse;
import com.example.project03_tuwaiqacademy.Service.AccountService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    //authority -> CUSTOMER
    @PostMapping("/create-account/{id}")
    public ResponseEntity createAccount(@PathVariable Integer id){
        accountService.createAccount(id);
        return ResponseEntity.status(200).body(new ApiResponse("new account created"));
    }

    //authority -> EMPLOYEE
    @PutMapping("/activate-account/{id}/{account_id}")
    public ResponseEntity activateAccount(@PathVariable Integer id,@PathVariable Integer account_id){
        accountService.activateAccount(id, account_id);
        return ResponseEntity.status(200).body(new ApiResponse("account activated"));
    }

    //authority -> CUSTOMER
    @GetMapping("/get-account-detail/{id}/{account_id}")
    public ResponseEntity getAccountDetails(@PathVariable Integer id,@PathVariable Integer account_id){
        return ResponseEntity.status(200).body(accountService.getAccountDetail(id, account_id));
    }

    //authority -> CUSTOMER
    @GetMapping("/get-accounts/{id}")
    public ResponseEntity getMyAccounts(@PathVariable Integer id){
        return ResponseEntity.status(200).body(accountService.getMyAccounts(id));
    }

    //authority -> CUSTOMER
    @PutMapping("/deposit/{id}/{account_id}/{amount}")
    public ResponseEntity depositMoney(@PathVariable Integer id,@PathVariable Integer account_id,@PathVariable double amount){
        accountService.depositMoney(id, account_id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("deposited"));
    }

    //authority -> CUSTOMER
    @PutMapping("/withdraw/{id}/{account_id}/{amount}")
    public ResponseEntity withdrawMoney(@PathVariable Integer id,@PathVariable Integer account_id,@PathVariable double amount){
        accountService.withdrawMoney(id, account_id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("withdrawn"));
    }

    //authority -> CUSTOMER
    @PutMapping("/transfer/{id}/from/{from}/to/{to}/{amount}")
    public ResponseEntity transfer(@PathVariable Integer id,@PathVariable Integer from,@PathVariable Integer to,@PathVariable double amount){
        accountService.transferBetweenAccounts(id, from, to, amount);
        return ResponseEntity.status(200).body(new ApiResponse("transfer successfully!!"));
    }

    //authority -> EMPLOYEE
    @PutMapping("/block-account/{id}/{account_id}")
    public ResponseEntity blockAccount(@PathVariable Integer id,@PathVariable Integer account_id){
        accountService.blockAccount(id, account_id);
        return ResponseEntity.status(200).body(new ApiResponse("account blocked!"));
    }

}
