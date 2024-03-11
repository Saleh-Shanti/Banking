package com.sshanti.accountservice.security;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/v1/customers/**")
                        .authenticated().anyRequest().hasAnyRole("ADMIN","READ", "ADD", "UPDATE", "DELETE")
                )
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                .formLogin(withDefaults());

        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("Saleh")
                .password(encoder.encode("Password"))
                .roles("ADMIN")
                .build();

        UserDetails readUser = User.withUsername("readUser")
                .password(encoder.encode("Password"))
                .roles("READ")
                .build();

        UserDetails writeUser = User.withUsername("addUser")
                .password(encoder.encode("Password"))
                .roles("ADD")
                .build();

        UserDetails deleteUser = User.withUsername("deleteUser")
                .password(encoder.encode("Password"))
                .roles("DELETE")
                .build();
        UserDetails updateUser = User.withUsername("updateUser")
                .password(encoder.encode("Password"))
                .roles("UPDATE")
                .build();

        return new InMemoryUserDetailsManager(admin, readUser, writeUser,deleteUser,updateUser);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
        return new DefaultAuthenticationEventPublisher(delegate);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

