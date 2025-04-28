package com.example.project03_tuwaiqacademy.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@AllArgsConstructor
public class CustomerDTO {

    @NotEmpty
//    @UniqueElements
    @Size(min = 4,max = 10, message = "username length should be between 4-10 characters")
    @Column(columnDefinition = "varchar(10) not null unique")
    @Check(constraints = "length(username)>=4 and length(username)<=10")
    private String username;
    @NotEmpty
    @Size(min = 6,message = "password should be at least 6 characters")
    @Column(columnDefinition = "varchar(255) not null")
    @Check(constraints = "length(password)>=6")
    private String password;
    @NotEmpty
    @Size(min = 2,max = 20, message = "username length should be between 2-20 characters")
    @Column(columnDefinition = "varchar(20) not null")
    @Check(constraints = "length(name)>=2 and length(name)<=20")
    private String name;
    @Email
//    @UniqueElements
    @Column(columnDefinition = "varchar(30) unique")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^(05[0-9]{8})$")
    private String phone_number;

}
