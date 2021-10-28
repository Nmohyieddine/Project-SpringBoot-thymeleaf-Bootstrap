package com.example.cmss_projet.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler successHandler;
    @Autowired
    private DataSource dataSource;

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal ,password as credentials , active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal , roles as role  from users_roles where username=?  ")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").successHandler(successHandler).and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");

        http.authorizeRequests().antMatchers("/PaiementSlip","/PayedSlip","/Bankcheck").hasRole("CONTA");
        http.authorizeRequests().antMatchers("/SlipVentilation","/SlipVentiler","/AllInvoiceVentilated","/exportSlipVentiler").hasRole("VENTILATION");
        http.authorizeRequests().antMatchers("/Slip").hasRole("BUREAUDORDRE");
        http.authorizeRequests().antMatchers("/Contracted").hasRole("CONFIGURA");



    }
}
