package com.inn.cafe.cafe.JWT;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

   @Autowired
    JwtFilter jwtFilter;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;


  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

 @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
      return builder.getAuthenticationManager();
    }

   
  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(customerUsersDetailsService);
  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    http.csrf(csrf->csrf.disable())
    .cors(cors->cors.disable())
    .authorizeHttpRequests(auth -> 
		         auth.requestMatchers("/user/login", "/user/signup", "/user/forgotPassword")		
		        .permitAll()
		        .anyRequest()
		        .authenticated()
		  )
    .exceptionHandling(ex->ex.authenticationEntryPoint(null))
    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  
  
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
     
    return http.build();
      //  .antMatchers("/user/login","/user/signup","/user/forgotPassword")
       
  }

}

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {

//     @Autowired
//     CustomerUsersDetailsService customerUsersDetailsService;

//     @Autowired
//     JwtFilter jwtFilter;
    
//      protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//         auth.userDetailsService(customerUsersDetailsService);
//      }

//       public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//     @Override
//     public AuthenticationManager authenticationManagerBean() throws Exception {
//         return super.authenticationManagerBean();
//     }
 
//      @Override
//      protected void configure(HttpSecurity http) throws Exception{
//        http
//        .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
//        .and()
//        .csrf().disable()
//        .authorizeRequests()
//        .antMatchers("/user/login","/user/signup","/user/forgotPassword")
//        .permitAll()
//        .anyRequest()
//        .authenticated()
//        .and().exceptionHandling()
//        .and()
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .addFilterAfter(jwtFilter, JwtFilter.class);
       

//      }

     
// }
