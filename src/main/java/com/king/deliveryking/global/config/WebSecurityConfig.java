package com.king.deliveryking.global.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
public class WebSecurityConfig {

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
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
                )

                // 인증 실패 시 로그인 페이지 리다이렉트 대신 401 응답
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }
}
