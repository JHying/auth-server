package tw.hyin.demo.config.web;

import tw.hyin.demo.repo.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 設置 Spring Security 規則
 *
 * @author H-yin
 */

/*
 * @EnableWebSecurity: 啟動 spring security 配置 = @Import({
 * WebSecurityConfiguration.class, SpringWebMvcImportSelector.class })
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserTokenRepository tokenRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// 關閉csrf驗證
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// jwt 可實現無狀態，不需要 session
                // 定義驗證規則
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()// 統一由 gateway 驗證
                .and()
                // 取得 token 之請求 (該 url) 交給 JwtLoginFilter 來處理
                .addFilterBefore(new JwtLoginFilter("/register", authenticationManager(), tokenRepository),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        // 帳戶驗證使用自定義類別
        auth.authenticationProvider(new JwtLoginProvider());
    }

}
