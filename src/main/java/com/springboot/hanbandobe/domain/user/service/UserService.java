package com.springboot.hanbandobe.domain.user.service;

import com.springboot.hanbandobe.entity.User;

public interface UserService {
    User getOneUserByUserNo(Long userNo);
}
