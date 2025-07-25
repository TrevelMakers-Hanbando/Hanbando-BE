package com.springboot.hanbandobe.domain.party.repository;

import com.springboot.hanbandobe.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
