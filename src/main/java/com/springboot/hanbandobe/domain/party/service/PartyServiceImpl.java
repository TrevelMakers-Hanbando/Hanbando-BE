package com.springboot.hanbandobe.domain.party.service;

import com.springboot.hanbandobe.domain.party.dto.PartyRequestDto;
import com.springboot.hanbandobe.domain.party.dto.PartyResponseDto;
import com.springboot.hanbandobe.domain.party.reposiroty.PartyRepository;
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
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;
    private final User_partyRepository userPartyRepository;

    @Override
    public void saveParty(User user, PartyRequestDto partyRequestDto) {
        Party party = Party.builder()
                .name(partyRequestDto.getName())
                .build();

        Party savedParty = partyRepository.save(party);

        User_party userParty = User_party.builder()
                .party(Party.builder().partyNo(savedParty.getPartyNo()).build())
                .user(User.builder().userNo(user.getUserNo()).build())
                .isParticipated(true)
                .isHost(true)
                .build();

        userPartyRepository.save(userParty);
    }

    @Override
    public PartyResponseDto getParty(Long partyNo) {
        PartyResponseDto partyResponseDto = partyRepository.findById(partyNo)
                .map(PartyResponseDto::new).orElseThrow(() -> new RuntimeException("존재하지 않는 파티입니다."));

        return partyResponseDto;
    }

    @Override
    public Page<PartyResponseDto> getParties(Pageable pageable, String title) {
        Page<PartyResponseDto> partyServiceDtos = partyRepository.findAllByNameContains(pageable, title)
                .map(PartyResponseDto::new);

        return partyServiceDtos;
    }

    @Override
    @Transactional
    public void updateParty(Long partyNo, User user, PartyRequestDto partyRequestDto) {
        Party party = partyRepository.findById(partyNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 파티입니다."));

        User_party userParty = userPartyRepository
                .getUser_partyByUserUserNoAndPartyPartyNo(user.getUserNo(), partyNo);

        if (!userParty.getIsHost()) {
            throw new RuntimeException("파티장이 아닙니다.");
        }

        party.setName(partyRequestDto.getName());
    }

    @Override
    public void deleteParty(Long partyNo, User user) {
        User_party userParty = userPartyRepository
                .getUser_partyByUserUserNoAndPartyPartyNo(user.getUserNo(), partyNo);

        if (!userParty.getIsHost()) {
            throw new RuntimeException("파티장이 아닙니다.");
        }

        partyRepository.deleteById(partyNo);
    }
}
