package com.springboot.hanbandobe.domain.party.service;

import com.springboot.hanbandobe.domain.party.dto.PartyRequestDto;
import com.springboot.hanbandobe.domain.party.repository.PartyRepository;
import com.springboot.hanbandobe.domain.user_party.service.User_partyService;
import com.springboot.hanbandobe.entity.Party;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final User_partyService user_partyService;

    @Override
    public void createParty(PartyRequestDto partyRequestDto) {

        Party party = Party.builder()
                .name(partyRequestDto.getName())
                .build();

        partyRepository.save(party);

//        user_partyService.createOneUser_party();
    }
}
