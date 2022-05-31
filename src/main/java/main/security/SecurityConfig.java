package main.security;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/bt/auth/singin").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/findGood/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/goods").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/goodsByName/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/sales").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/findSale/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/salesByName/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/warehouse1").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/findWarehouse1/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/warehouse1ByName/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/warehouse1ByGoodId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/warehouse2ByGoodId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/warehouse1PriorityUnder/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/warehouse2").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/findWarehouse2/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/warehouse2ByName/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/warehouse2Priority/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/addGood").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/bt/deleteGood").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/bt/updateGood").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/bt/updateWarehouse1").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/bt/addSale").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/bt/deleteSale").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/bt/addWarehouse1").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/bt/deleteWarehouse1").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/bt/addWarehouse2").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/bt/deleteWarehouse2").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
