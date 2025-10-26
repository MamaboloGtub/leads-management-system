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
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
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

    private static final String LEADS_PATH = "/api/leads/**";
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityWebFilterChain leadSecurityWebFilterChain(ServerHttpSecurity http) {
        ReactiveAuthenticationManager authManager = authentication -> {
            String token = (String) authentication.getCredentials();
            return jwtUtil.validateAndGetUsername(token)
                    .map(u -> {
                        List<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                        return new UsernamePasswordAuthenticationToken(u, token, auths);
                    });
        };

        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(authManager);

        jwtAuthFilter.setServerAuthenticationConverter(ex -> {
            String authHeader = ex.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                return Mono.just(new UsernamePasswordAuthenticationToken(null, token));
            }
            return Mono.empty();
        });

        jwtAuthFilter.setRequiresAuthenticationMatcher(
            new OrServerWebExchangeMatcher(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, LEADS_PATH),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.PUT, LEADS_PATH),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE, LEADS_PATH),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/api/leads/*")
            )
        );

        return http
                .csrf(CsrfSpec::disable)
                .httpBasic(HttpBasicSpec::disable)
                .formLogin(FormLoginSpec::disable)
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
