package com.king.deliveryking.global.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 로그인 사용자의 id를 컨트롤러 파라미터로 주입한다.
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "userId") // UserDetailsImpl.getUserId() 호출
public @interface LoginUserId {

}