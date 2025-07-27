package com.springboot.hanbandobe.domain.user.service;

import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
