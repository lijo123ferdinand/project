// package com.example.capstone.expense;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfiguration {

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests()
//             .antMatchers("/login", "/signup").permitAll() // Allow access to login and signup pages
//             .anyRequest().authenticated() // Require authentication for all other requests
//             .and()
//             .formLogin()
//                 .loginPage("/login") // Use custom login page
//                 .defaultSuccessUrl("/dashboard") // Redirect to dashboard after successful login
//                 .permitAll() // Allow access to the login page
//             .and()
//             .logout()
//                 .logoutSuccessUrl("/login") // Redirect to login page after logout
//                 .permitAll(); // Allow access to the logout functionality
//     }
// }

