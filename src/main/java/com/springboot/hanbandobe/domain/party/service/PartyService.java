package com.springboot.hanbandobe.domain.party.service;

import com.springboot.hanbandobe.domain.party.dto.PartyRequestDto;
import com.springboot.hanbandobe.domain.party.dto.PartyResponseDto;
import com.springboot.hanbandobe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartyService {
    void saveParty(User user, PartyRequestDto partyRequestDto);

    PartyResponseDto getParty(Long partyNo);

    Page<PartyResponseDto> getParties(Pageable pageable, String title);

    void updateParty(Long partyNo, User user, PartyRequestDto partyRequestDto);

    void deleteParty(Long partyNo, User user);
}
