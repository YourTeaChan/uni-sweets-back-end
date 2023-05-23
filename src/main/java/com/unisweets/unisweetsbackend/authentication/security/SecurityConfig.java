package com.unisweets.unisweetsbackend.authentication.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.unisweets.unisweetsbackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->
                new UserDetailsImpl(
                        userRepository
                                .findByEmail(username)
                                .orElseThrow(() -> new BadCredentialsException("Email not found"))
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withSecretKey(
                        new SecretKeySpec(
                                SECRET_KEY.getBytes(),
                                "HS256"))
                .build();
        jwtDecoder.setJwtValidator(tokenValidator());
        return jwtDecoder;
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWKSource<SecurityContext> jwkSource =
                new ImmutableSecret<>(
                        new SecretKeySpec(
                                SECRET_KEY.getBytes(),
                                "HS256"));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public OAuth2TokenValidator<Jwt> tokenValidator() {
        return new DelegatingOAuth2TokenValidator<>(
                List.of(new JwtTimestampValidator(
                        Duration.of(0,
                                ChronoUnit.SECONDS))));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/auth/**"
                )
                .permitAll()
//                .requestMatchers(
//                        "api/v1/auth/sign-up",
//                        "api/v1/auth/sign-in")
//                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .oauth2ResourceServer()
//                .authenticationEntryPoint(
//                        (request, response, authException) ->
//                                handlerExceptionResolver
//                                        .resolveException(
//                                                request,
//                                                response,
//                                                null,
//                                                new RuntimeException(authException.getMessage())))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}