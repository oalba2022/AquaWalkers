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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.aquawalkers.security.jwt.UnauthorizedHandlerJwt;
import com.example.aquawalkers.security.jwt.JwtRequestFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
  	private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

	@Autowired
	private RepositoryUserDetailsService userDetailService;

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
		http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
		http
				.securityMatcher("/api/**")
				.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

		http

				.authorizeHttpRequests(authorize -> authorize
						// PRIVATE ENDPOINTS
						.requestMatchers(HttpMethod.POST,"/api/zapatilla").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET,"/api/zapatillas").hasAnyRole("USER","ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/zapatilla/*").hasAnyRole("USER","ADMIN")
						.requestMatchers(HttpMethod.PUT,"/api/zapatilla/*").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE,"/api/zapatilla/*").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyRole("USER","ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/zapatilla/*/comment").hasAnyRole("USER","ADMIN")
						.requestMatchers(HttpMethod.POST,"/api/zapatilla/*/image").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE,"/api/users/deleteuser/*").hasRole("ADMIN")
						//PUBLIC ENDPOINTS
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
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());
		http
			.authorizeHttpRequests(authorize -> authorize
					// PUBLIC PAGES
					.requestMatchers("/inicio").permitAll()
					.requestMatchers("/404").permitAll()
					.requestMatchers("/error").permitAll()
                    .requestMatchers("/allshoes").permitAll()
					.requestMatchers("/styles/**", "/images/**").permitAll()
					.requestMatchers("/loginerror").permitAll()
					.requestMatchers("/zapatilla/**").permitAll()
					.requestMatchers("/zapatillas").permitAll()
					.requestMatchers("/").permitAll()
					.requestMatchers("/login").permitAll()
					.requestMatchers("/sobre-nosotros").permitAll()
					.requestMatchers("/register").permitAll()
					// PRIVATE PAGES
					.requestMatchers("/newshoe").hasAnyRole("ADMIN")
                    .requestMatchers("/modifyshoe/*").hasAnyRole("ADMIN")
					.requestMatchers("/deleteshoe/*").hasAnyRole("ADMIN")
					.requestMatchers("/deleteuser/*").hasAnyRole("ADMIN")
					.requestMatchers("/users").hasRole("ADMIN")
					.requestMatchers("/deletecomment/*").hasAnyRole("USER","ADMIN")
					.requestMatchers("/usercard").hasAnyRole("USER","ADMIN")
					.requestMatchers("/carrito").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/uploadfile").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/zapatilla/*/escribirComentario").hasAnyRole("USER","ADMIN")
					.requestMatchers("/addcarrito/{id}").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/comprar").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/deleteme").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/modifyuser").hasAnyRole("USER", "ADMIN")


			)
			.formLogin(formLogin -> formLogin
					.loginPage("/login")
					.failureUrl("/loginerror")
					.defaultSuccessUrl("/zapatillas",true)
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
