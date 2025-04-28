package com.example.project03_tuwaiqacademy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {


    @Id
    private Integer id;
    @NotEmpty
    private String position;
    @NotNull
    @PositiveOrZero(message = "salary cannot be negative")
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;
    //2. Employee Model:
        //• id: Generated automatically.
        //• position:
           //- Cannot be null.
        //• salary:
            //- Cannot be null.
            //- Must be a non-negative decimal number.


}
