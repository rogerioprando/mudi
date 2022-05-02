package br.com.alura.mvc.mudi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout"))
                .csrf().disable();      // 403 forbidden
    }


    /*
        Indica pro Spring security que vamos trabalhar com JDBCAutenthication.
        Precisa passar pro spring um datasource (que é onde consegue as conexões com banco de dados)
        Faz isso via injeção:
            @Autowired
            DataSource dataSource;

        O passwordEncoder ecripta as senhas.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//        // Cria um usuário (EXECUTAR APENAS UMA VEZ)
//        UserDetails user =
//                User.builder()
//                        .username("master")
//                        .password(encoder.encode("kch031r4"))
//                        .roles("ADM")
//                        .build();

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder);
                //.withUser(user);

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("rogerio")
                        .password("rogerio")
                        .roles("ADM")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
