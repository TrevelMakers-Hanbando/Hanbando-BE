package com.springboot.hanbandobe.domain.user_party.service;

import com.springboot.hanbandobe.domain.user_party.dto.User_partyRequestDto;
import com.springboot.hanbandobe.domain.user_party.dto.User_partyResponseDto;
import com.springboot.hanbandobe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface User_partyService {
    Page<User_partyResponseDto> getUser_partiesByParty_PartyNo(Pageable pageable, Long partyNo);

    void saveUser_party(User_partyRequestDto userPartyRequestDtoy);

    void updateUser_party(Long userPartyNo, User user, User_partyRequestDto userPartyRequestDto);

    void deleteUser_party(Long userPartyNo, User user);
}
