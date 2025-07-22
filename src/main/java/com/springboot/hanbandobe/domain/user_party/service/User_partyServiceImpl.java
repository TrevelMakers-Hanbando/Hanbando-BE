package com.springboot.hanbandobe.domain.user_party.service;

import com.springboot.hanbandobe.domain.party.service.PartyService;
import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.domain.user.service.UserService;
import com.springboot.hanbandobe.domain.user_party.repository.User_partyRepository;
import com.springboot.hanbandobe.entity.Party;
import com.springboot.hanbandobe.entity.User;
import com.springboot.hanbandobe.entity.User_party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class User_partyServiceImpl implements User_partyService {

    private final User_partyRepository user_partyRepository;
    private final UserService userService;
//    private final PartyService partyService;

    @Override
    public void createOneUser_party(Long userNo, Long partyNo) {

        User user = userService.getOneUserByUserNo(userNo);

//        Party party =
//
//        User_party user_party = User_party.builder()
//                .user(user)
//                .party()
//                .isParticipated(true)
//                .build();
//
//        user_partyRepository.save();
    }
}
