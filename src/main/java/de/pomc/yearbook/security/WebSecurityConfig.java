package de.pomc.yearbook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/users").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/profile", true)
                .and()
                .rememberMe()
                .and()
            .logout()
                .logoutSuccessUrl("/journals/")
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails philip = User.builder()
                .username("philip")
                .password(passwordEncoder.encode("123456"))
                .roles("ADMIN")
                .build();

        UserDetails malte = User.builder()
                .username("malte")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(philip, malte);
    }
}
