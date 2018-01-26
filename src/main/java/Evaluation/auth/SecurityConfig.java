package Evaluation.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();//在这里可以返回一个自定义的密码加密器，现在用spring security提供的
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //.loginPage("/authentication/require")//使用controller，可以判定是html请求还是api请求
                .loginPage("/sign-in.html")
                .loginProcessingUrl("/authentication/form")//sign-in.html上的action路径，复写了usernamePasswordAuthenticationFilter的默认提供的/login登陆页处理器的方法
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)//这两句可以实现第二个用户不能登陆（在第一个用户登陆时）
                .expiredUrl("/session/invalid")
                //.expiredSessionStrategy()
                .and()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasAuthority("admin")//此选中的url要有admin的Authority,todo 验证：注意antMatchers的先后顺序有覆盖作用？
                .and()
                .authorizeRequests()
                .antMatchers("/sign-in.html","/authentication/require","/commentTeacher/test","/session/invalid","/commentTeacher/**","/admin/test/**").permitAll()//antMatchers匹配上的url都不需要认证就可以访问
                .anyRequest()//下面任意url都要认证和授权
                .authenticated()

                .and()
                .csrf().disable();
    }



//    /**
//     * 自定义md5+salt的加密手段
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserService).passwordEncoder(new PasswordEncoder(){
//
//            public String encode(CharSequence rawPassword) {//实现PasswordEncoder的encode和matches
//                return MD5Util.encode((String)rawPassword);
//            }
//
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                System.out.println("进入matches："+rawPassword.toString());
//                System.out.println(MD5Util.encode(rawPassword.toString()));
//                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
//            }}); //user Details Service验证
//    }
//
//    @Autowired
//    MyUserDetailService customUserService;



}
