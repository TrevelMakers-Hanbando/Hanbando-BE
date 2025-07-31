package com.springboot.hanbandobe.domain.auth.service;

import com.springboot.hanbandobe.domain.auth.PrincipalDetails;
import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.entity.User;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.RegExp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class userDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isPresent()) {
            return new PrincipalDetails(user.get());
        }

        // 사용자 없으면 예외 발생
        throw new UsernameNotFoundException("해당 이메일을 갖는 사용자가 존재하지 않습니다: " + username);
    }
}
