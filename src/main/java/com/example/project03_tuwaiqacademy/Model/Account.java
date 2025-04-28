package com.example.project03_tuwaiqacademy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "account number shouldn't be empty")
    @Pattern(regexp = "^([0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4})$")
    @Column(columnDefinition = "varchar(20) not null")
    private String account_number;
    @NotNull(message = "cannot be null")
    @PositiveOrZero(message = "balance cannot be negative")
    @Column(columnDefinition = "double not null")
    private Double balance;
    @Column(columnDefinition = "bool")
    private Boolean is_active=false;

    @ManyToOne
    @JsonIgnore
    private Customer customer;
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
}
