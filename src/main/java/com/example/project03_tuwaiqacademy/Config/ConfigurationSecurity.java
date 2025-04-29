package com.example.project03_tuwaiqacademy.Config;

import com.example.project03_tuwaiqacademy.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/**").permitAll()
//                .requestMatchers("/api/v1/auth/**").hasAnyAuthority("ADMIN","CUSTOMER","EMPLOYEE")
                .requestMatchers("/api/v1/auth/assign-admin","/api/v1/auth/register-customer",
                        "/api/v1/auth/register-employee").permitAll() // ---------------------------------------------
                .requestMatchers("/api/v1/account/create-account","/api/v1/account/get-account-detail/{account_id}",
                        "/api/v1/account/get-accounts","/api/v1/account/deposit/{account_id}/{amount}",
                        "/api/v1/account/withdraw/{account_id}/{amount}","/api/v1/account/transfer/from/{from}/to/{to}/{amount}",
                        "/api/v1/auth/update-customer").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/auth/update-employee","/api/v1/account/activate-account/{account_id}",
                        "/api/v1/account/block-account/{account_id}","/api/v1/auth/get-customers").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/auth/get-all","/api/v1/auth/delete-user/user/{user_id}").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return httpSecurity.build();

        // requestMatcher for all users like (user / admin / customer...etc)
        // without duplication


    }

}
