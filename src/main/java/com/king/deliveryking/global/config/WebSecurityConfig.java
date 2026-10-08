package com.king.deliveryking.global.config;

import com.king.deliveryking.global.security.JwtAuthorizationFilter;
import com.king.deliveryking.global.security.JwtUtil;
import com.king.deliveryking.global.security.SecurityErrorHandler;
import com.king.deliveryking.global.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@EnableMethodSecurity(securedEnabled = true) // @Secured 사용
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final SecurityErrorHandler securityErrorHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // REST API 서버이므로 화면 기반 기능 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 제거
                .httpBasic(AbstractHttpConfigurer::disable) // 브라우저 Basic 인증 팝업 제거
                .logout(AbstractHttpConfigurer::disable)

                // JWT 사용 → 세션 미사용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/api/auth/**").permitAll() // 로그인, 회원가입 등 인증 API 허용
                        .requestMatchers("/error").permitAll() // 에러 응답이 401로 바뀌지 않도록 허용
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll() // Swagger 허용
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
                )

                // 요청마다 JWT 검증
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)

                // 인증 실패(401), 권한 부족(403) 시 ErrorResponse JSON으로 응답
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(securityErrorHandler)
                        .accessDeniedHandler(securityErrorHandler));

        return http.build();
    }
}
