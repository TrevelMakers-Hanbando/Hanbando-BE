package com.springboot.hanbandobe.domain.party.service;

import com.springboot.hanbandobe.domain.party.dto.PartyRequestDto;

import java.util.List;

public interface PartyService {

    void createParty(PartyRequestDto partyRequestDto);
}
