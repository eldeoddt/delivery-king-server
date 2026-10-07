package com.king.deliveryking.user.service;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.user.dto.request.SignupRequestDTO;
import com.king.deliveryking.user.entity.User;
import com.king.deliveryking.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 사장님 토큰
    private final String OWNER_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDTO requestDto) {
        String username = requestDto.username();
        String password = passwordEncoder.encode(requestDto.password());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.email();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRole role = UserRole.CUSTOMER;
        if (requestDto.owner()) {
            if (!OWNER_TOKEN.equals(requestDto.ownerToken())) {
                throw new IllegalArgumentException("사장님 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.OWNER;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
}
