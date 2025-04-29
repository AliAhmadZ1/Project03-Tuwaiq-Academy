package com.example.project03_tuwaiqacademy.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$")
    @Column(columnDefinition = "varchar(8) not null")
    @Check(constraints = "role='CUSTOMER' or role='EMPLOYEE' or role = 'ADMIN'")
    private String role;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Employee employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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


}
