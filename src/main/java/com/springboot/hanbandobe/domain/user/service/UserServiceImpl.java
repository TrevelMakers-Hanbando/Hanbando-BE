package com.springboot.hanbandobe.domain.user.service;

import com.springboot.hanbandobe.domain.handler.exception.UserException;
import com.springboot.hanbandobe.domain.handler.message.ExceptionMessage;
import com.springboot.hanbandobe.domain.role.repository.RoleRepository;
import com.springboot.hanbandobe.domain.user.dto.UserRegisterDto;
import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.entity.Role;
import com.springboot.hanbandobe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User getOneUserByUserNo(Long userNo) {

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        return user;
    }

    @Override
    @Transactional
    public User join(UserRegisterDto dto) {

        checkEmailDuplication(dto.getEmail());

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new UserException(ExceptionMessage.ROLE_NOT_FOUND));

        // 사용자 생성
        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .name(dto.getName())
                .role(role)
                .isDeleted(false)
                .build();

        return userRepository.save(user);
    }

    // 현재 사용자 확인
    public User getCurrentUser(Principal principal) {
        String userEmail = principal.getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND));
    }

    // email 중복 확인
    public void checkEmailDuplication(String email){

        Optional<User> findUsers = userRepository.findByEmail(email);
        if(findUsers.isPresent()){
            throw new UserException(ExceptionMessage.EMAIL_ALREADY_EXIST);
        }
    }




}
