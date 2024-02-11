package ru.dzheb.springdatajpa.security;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(configurer -> configurer
                        //.csrf((csrf) -> csrf.ignoringRequestMatchers("/api/v1/**"));
                        .requestMatchers("/api/v1/").permitAll()
                        .requestMatchers("/ui/reader/**").hasAuthority("reader")
                        .requestMatchers("/ui/issues/**").hasAuthority("admin")
                        .requestMatchers("/ui/books/**").authenticated()
                        .requestMatchers("/api/**").permitAll()
//                              .anyRequest().denyAll()
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
//                .formLogin()
//       https://docs.spring.io/spring-security/site/docs/5.5.4/guides/form-javaconfig.html
//                .loginPage("/login")
    }
}
