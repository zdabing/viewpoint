package com.viewpoint.config;


import com.viewpoint.properties.SecurityConstants;
import com.viewpoint.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * security 配置
 */
@EnableWebSecurity
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        applyPasswordAuthenticationConfig(http);
        http
                .authorizeRequests().antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL
                        ,securityProperties.getBrowser().getLoginPage()
                        , "/layui/**","/js/**","/css/**","/images/*","/fonts/**","/**/*.png","/**/*.jpg").permitAll()
                    .anyRequest()
                    .authenticated()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl(securityProperties.getBrowser().getLoginPage())
                    .and()
                .csrf().disable();
    }
}
