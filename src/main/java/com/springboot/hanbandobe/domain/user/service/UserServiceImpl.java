package com.springboot.hanbandobe.domain.user.service;

import com.springboot.hanbandobe.domain.handler.exception.UserException;
import com.springboot.hanbandobe.domain.handler.message.ExceptionMessage;
import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getOneUserByUserNo(Long userNo) {

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        return user;
    }

    // 현재 사용자 확인
    public User getCurrentUser(Principal principal) {
        String userEmail = principal.getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND));
    }




}
