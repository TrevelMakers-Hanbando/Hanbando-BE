package com.springboot.hanbandobe.domain.user_party.service;

import com.springboot.hanbandobe.domain.user_party.dto.User_partyRequestDto;
import com.springboot.hanbandobe.domain.user_party.dto.User_partyResponseDto;
import com.springboot.hanbandobe.domain.user_party.repository.User_partyRepository;
import com.springboot.hanbandobe.entity.Party;
import com.springboot.hanbandobe.entity.User;
import com.springboot.hanbandobe.entity.User_party;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class User_partyServiceImpl implements User_partyService {
    private final User_partyRepository userPartyRepository;

    @Override
    public Page<User_partyResponseDto> getUser_partiesByParty_PartyNo(Pageable pageable, Long partyNo) {
        Page<User_partyResponseDto> userPartyResponseDtos =  userPartyRepository
                .getUser_partiesByParty_PartyNo(pageable, partyNo)
                .map(User_partyResponseDto::new);

        return userPartyResponseDtos;
    }

    @Override
    public void saveUser_party(User_partyRequestDto userPartyRequestDto) {
        User_party userParty = User_party.builder()
                .party(Party.builder().partyNo(userPartyRequestDto.getPartyNo()).build())
                .user(User.builder().userNo(userPartyRequestDto.getUserNo()).build())
                .build();

        userPartyRepository.save(userParty);
    }

    @Override
    @Transactional
    public void updateUser_party(Long partyNo, User user, User_partyRequestDto userPartyRequestDto) {
        User_party userParty = userPartyRepository.findById(partyNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 파티입니다."));

        // User_party 테이블에서 is_participated 타입(boolean) 이상함
        // True, False - Not Null 이지만

        userParty.setIsParticipated(userPartyRequestDto.isParticipated());
    }

    @Override
    public void deleteUser_party(Long partyNo, User user) {
        User_party userParty = userPartyRepository
                .getUser_partyByUserUserNoAndPartyPartyNo(user.getUserNo(), partyNo);

        if (!userParty.getIsHost()) {
            throw new RuntimeException("파티장이 아닙니다.");
        }

        userPartyRepository.deleteById(partyNo);
    }
}
