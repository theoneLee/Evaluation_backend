package Evaluation.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter{

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/authentication/require")//使用controller，可以判定是html请求还是api请求
                //.loginPage("/sign-in.html")
                .loginProcessingUrl("/authentication/form")//sign-in.html上的action路径，复写了usernamePasswordAuthenticationFilter的默认提供的/login登陆页处理器的方法

                .and()
                .authorizeRequests()
                .antMatchers("/sign-in.html","/authentication/require").permitAll()//antMatchers匹配上的url都不需要认证就可以访问

                .anyRequest()//下面任意url都要认证和授权
                .authenticated()

                .and()
                .csrf().disable();
    }
}
