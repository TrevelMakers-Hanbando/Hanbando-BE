package com.springboot.hanbandobe.domain.user_party.repository;

import com.springboot.hanbandobe.entity.User_party;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_partyRepository extends JpaRepository<User_party, Long> {
    Page<User_party> getUser_partiesByParty_PartyNo(Pageable pageable, Long partyNo);

    User_party getUser_partyByUserUserNoAndPartyPartyNo(Long userNo, Long partyNo);
}
