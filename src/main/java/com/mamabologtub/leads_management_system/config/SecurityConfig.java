package com.mamabologtub.leads_management_system.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import com.mamabologtub.leads_management_system.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @Author Tshepo M Mahudu on Oct 19, 2025.
 */
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityWebFilterChain leadSecurityWebFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(new ReactiveAuthenticationManager() {

            @Override
            public Mono<Authentication> authenticate(Authentication authentication) {
                String token = (String) authentication.getCredentials();
                return jwtUtil.validateAndGetUsername(token)
                        .map(u -> {
                            List<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                            return new UsernamePasswordAuthenticationToken(u, token, auths);
                        });
            }
        });

        jwtAuthFilter.setServerAuthenticationConverter(ex -> {
            String authHeader = ex.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                return Mono.just(new UsernamePasswordAuthenticationToken(null, token));
            }
            return Mono.empty();
        });

        jwtAuthFilter.setRequiresAuthenticationMatcher(
            ServerWebExchangeMatchers.pathMatchers("/api/leads/**", "/api/other-protected-paths/**")
        );

        return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/leads").permitAll()
                        .anyExchange().authenticated()
                        )
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

}
