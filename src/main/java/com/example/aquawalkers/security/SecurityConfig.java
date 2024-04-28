package com.example.aquawalkers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.aquawalkers.security.jwt.UnauthorizedHandlerJwt;
import com.example.aquawalkers.security.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
    public UserDetailsService userDetailService;

	@Autowired
  	private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	@Order(1)
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		
		http.authenticationProvider(authenticationProvider());
		
		http
			.securityMatcher("/api")
			.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));
		
		http
			.authorizeHttpRequests(authorize -> authorize
                    // PRIVATE ENDPOINTS
                    .requestMatchers(HttpMethod.POST,"/api/zapatilla").hasRole("ADMIN")
					.requestMatchers(HttpMethod.GET,"/api/zapatillas").hasRole("USER")
					.requestMatchers(HttpMethod.GET, "/api/zapatilla/**").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT,"/api/zapatilla/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE,"/api/zapatilla/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/zapatilla/**/image").hasRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/zapatilla/**/comment").hasRole("USER")
					// PUBLIC ENDPOINTS
					.anyRequest().permitAll()
			);
		
        // Disable Form login Authentication
        http.formLogin(formLogin -> formLogin.disable());

        // Disable CSRF protection (it is difficult to implement in REST APIs)
        http.csrf(csrf -> csrf.disable());

        // Disable Basic Authentication
        http.httpBasic(httpBasic -> httpBasic.disable());

        // Stateless session
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Add JWT Token filter
			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

			return http.build();
	}
	@Bean
	@Order(2)
	public SecurityFilterChain apiUsersFilterChain(HttpSecurity http) throws Exception {
		http
				.securityMatcher("/api/users")
				.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));
		http
				.authorizeHttpRequests(authorize -> authorize
						// PRIVATE ENDPOINTS
						.requestMatchers(HttpMethod.POST, "/api/users/register").hasRole("USER")
						.anyRequest().permitAll()
				);

		return http.build();
	}
	@Bean
    @Order(3)
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
		
		http.authenticationProvider(authenticationProvider());
		
		http
			.authorizeHttpRequests(authorize -> authorize
					// PUBLIC PAGES
					.requestMatchers("/").permitAll()
					.requestMatchers("/error").permitAll()
                    .requestMatchers("/allshoes/*").permitAll()
					.requestMatchers("/styles/**", "/images/**").permitAll()
					// PRIVATE PAGES
					.requestMatchers("/newshoe").hasAnyRole("ADMIN")
                    .requestMatchers("/modifyshoe/*").hasAnyRole("ADMIN")
					.requestMatchers("/deleteshoe/*").hasAnyRole("ADMIN")
					.requestMatchers("zapatilla/*/escribirComentario").hasRole("USER")
					.requestMatchers("/deletecomment/*").hasRole("USER")
					.requestMatchers("/zapatilla/*")
			)
			.formLogin(formLogin -> formLogin
					.loginPage("/login")
					.failureUrl("/loginerror")
					.defaultSuccessUrl("/")
					.permitAll()
			)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.permitAll()
			);

		return http.build();
	}

}