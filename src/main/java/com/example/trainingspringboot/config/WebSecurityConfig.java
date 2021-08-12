package com.example.trainingspringboot.config;

import com.example.trainingspringboot.filters.MyCorsFilter;
import com.example.trainingspringboot.jwt.AuthEntryPointJwt;
import com.example.trainingspringboot.filters.AuthJwtFilter;
import com.example.trainingspringboot.userDetail.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthJwtFilter authenticationJwtTokenFilter() {
        return new AuthJwtFilter();
    }

    @Bean
    public MyCorsFilter corsFilter() {
        return new MyCorsFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // for role _ hierarchy but not work yet.
//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        String hierarchy = "ADMIN > MANAGER \n MANAGER > SENIOR \n SENIOR > JUNIOR \n JUNIOR > FRESHER";
//        roleHierarchy.setHierarchy(hierarchy);
//        return roleHierarchy;
//    }
//
//    @Bean
//    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy());
//        return expressionHandler;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // for signup, sign in
                .antMatchers("/api/auth/*").permitAll()
                .antMatchers(HttpMethod.GET,"/api/roles").permitAll()

//                .antMatchers("/api/users/*").hasAnyAuthority("ADMIN", "MANAGER", "SENIOR")
//                .antMatchers("/api/permissions/*").hasAnyAuthority("ADMIN", "MANAGER")

                .antMatchers(HttpMethod.POST, "/api/*").hasAuthority("CREATE")
                .antMatchers(HttpMethod.PUT, "/api/*/*").hasAuthority("UPDATE")
                .antMatchers(HttpMethod.DELETE, "/api/*/*").hasAuthority("DELETE")
                .anyRequest().authenticated();


        http.addFilterBefore(corsFilter(), ChannelProcessingFilter.class);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}