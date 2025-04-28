package com.example.project03_tuwaiqacademy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^(05[0-9]{8})$")
    private String phone_number;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private Set<Account> accounts;

    //3. Customer Model:
        //• id: Generated automatically.
        //• phoneNumber:
            //- Cannot be null.
            //- Must start with "05"
}
