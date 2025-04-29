package com.example.project03_tuwaiqacademy.Controller;

import com.example.project03_tuwaiqacademy.Api.ApiResponse;
import com.example.project03_tuwaiqacademy.Model.Customer;
import com.example.project03_tuwaiqacademy.Model.User;
import com.example.project03_tuwaiqacademy.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    //authority -> CUSTOMER
    @GetMapping("/get-accounts")
    public ResponseEntity getMyAccounts(@AuthenticationPrincipal User customer){
        return ResponseEntity.status(200).body(accountService.getMyAccounts(customer.getId()));
    }

    //authority -> CUSTOMER
    @GetMapping("/get-account-detail/{account_id}")
    public ResponseEntity getAccountDetails(@AuthenticationPrincipal User customer,@PathVariable Integer account_id){
        return ResponseEntity.status(200).body(accountService.getAccountDetail(customer.getId(), account_id));
    }

    //authority -> CUSTOMER
    @PostMapping("/create-account")
    public ResponseEntity createAccount(@AuthenticationPrincipal User customer){
        accountService.createAccount(customer.getId());
        return ResponseEntity.status(200).body(new ApiResponse("new account created"));
    }

    //authority -> EMPLOYEE
    @PutMapping("/activate-account/{account_id}")
    public ResponseEntity activateAccount(@AuthenticationPrincipal User employee,@PathVariable Integer account_id){
        accountService.activateAccount(employee.getId(), account_id);
        return ResponseEntity.status(200).body(new ApiResponse("account activated"));
    }

    //authority -> EMPLOYEE
    @PutMapping("/block-account/{account_id}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal User employee,@PathVariable Integer account_id){
        accountService.blockAccount(employee.getId(), account_id);
        return ResponseEntity.status(200).body(new ApiResponse("account blocked!"));
    }

    @DeleteMapping("/delete-account/{account_id}")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal Customer customer,@PathVariable Integer account_id){
        accountService.deleteAccount(customer.getId(), account_id);
        return ResponseEntity.status(200).body(new ApiResponse("account deleted"));
    }


    //authority -> CUSTOMER
    @PutMapping("/deposit/{account_id}/{amount}")
    public ResponseEntity depositMoney(@AuthenticationPrincipal User customer,@PathVariable Integer account_id,@PathVariable double amount){
        accountService.depositMoney(customer.getId(), account_id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("deposited"));
    }

    //authority -> CUSTOMER
    @PutMapping("/withdraw/{account_id}/{amount}")
    public ResponseEntity withdrawMoney(@AuthenticationPrincipal User customer,@PathVariable Integer account_id,@PathVariable double amount){
        accountService.withdrawMoney(customer.getId(), account_id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("withdrawn"));
    }

    //authority -> CUSTOMER
    @PutMapping("/transfer/from/{from}/to/{to}/{amount}")
    public ResponseEntity transfer(@AuthenticationPrincipal User customer,@PathVariable Integer from,@PathVariable Integer to,@PathVariable double amount){
        accountService.transferBetweenAccounts(customer.getId(), from, to, amount);
        return ResponseEntity.status(200).body(new ApiResponse("transfer successfully!!"));
    }

}
