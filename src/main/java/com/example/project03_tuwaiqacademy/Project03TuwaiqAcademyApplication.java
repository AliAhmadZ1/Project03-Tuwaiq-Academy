package com.example.project03_tuwaiqacademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Project03TuwaiqAcademyApplication {

    public static void main(String[] args) {
        SpringApplication.run(Project03TuwaiqAcademyApplication.class, args);
    }

}

//1. User Model:
        //• id: Generated automatically.
        //• username:
        //- Cannot be null.
        //- Length must be between 4 and 10 characters. - Must be unique.
        //• password:
        //- Cannot be null.
        //- Length must be at least 6 characters.
        //• name:
        //- Cannot be null.
        //- Length must be between 2 and 20 characters.
        //• email:
        //- Must be a valid email format. - Must be unique. •
        //role:
        //- Must be either "CUSTOMER" , "EMPLOYEE" or "ADMIN" only.

//2. Employee Model:
        //• id: Generated automatically.
        //• position:
        //- Cannot be null.
        //• salary:
        //- Cannot be null.
        //- Must be a non-negative decimal number.

//3. Customer Model:
        //• id: Generated automatically.
        //• phoneNumber:
        //- Cannot be null.
        //- Must start with "05"

//4. Account Model:
        //• id: Generated automatically.
        //• accountNumber:
        //- Cannot be null.
        //- Must follow a specific format (e.g., "XXXX-XXXX-XXXX-XXXX").
        //• balance:
        //- Cannot be null.
        //- Must be a non-negative decimal number.
        //• isActive:
        //- By default, false.

//Relations:
    //User can be an employee or a customer (OneToOne).
    //One Customer May Have Many Accounts (OneToMany).
//
//Endpoints:
    //1. CRUD operations
    //2. Create a new bank account
    //3. Active a bank account
    //4. View account details
    //5. List user's accounts
    //6. Deposit and withdraw money
    //7. Transfer funds between accounts
    //8. Block bank account

//*********************************************
//Note: Add Authorization to All Endpoints
