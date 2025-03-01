package com.tyut.travel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//Security配置类
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启注解鉴权配置
@SuppressWarnings("all")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //springSecurity配置

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //配置登录
        http.formLogin()
                .loginPage("/backstage/admin_login") //配置自定义登录页面
                .usernameParameter("username")       //配置表单中用户名项的name属性
                .passwordParameter("password")       //配置表单中密码项的name属性
                .loginProcessingUrl("/backstage/admin/login")  //配置表单提交路径
                .successForwardUrl("/backstage/index")       //配置认证成功要跳转的页面
                .failureForwardUrl("/backstage/admin_fail"); //配置认证失败要跳转的页面


        //配置拦截器
        http.authorizeRequests()
                .antMatchers("/backstage/admin/login").permitAll()    //登录资源需要放行
                .antMatchers("/backstage/admin_login").permitAll()    //登录页面需要放行
                .antMatchers("/backstage/admin_fail").permitAll()     //登录失败页面需要放行
                .antMatchers("/**/*.css","/**/*.js").permitAll()    //静态资源需要放行
                .antMatchers("/backstage/**").authenticated();        //其余资源都需要认证


        //退出登录配置
        http.logout()
                .logoutUrl("/backstage/admin/logout")           //退出登录请求路径
                .logoutSuccessUrl("/backstage/admin_login")        //退出登录成功后跳转的页面
                .clearAuthentication(true)                      //清除认证信息
                .invalidateHttpSession(true);                   //清除session信息


        //异常处理配置
        http.exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler());

        // 关闭csrf防护
        http.csrf().disable();
        //开启跨域访问
        http.cors();
    }

    //配置密码加密器
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
