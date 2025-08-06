package com.springboot.hanbandobe.domain.party.reposiroty;

import com.springboot.hanbandobe.entity.Party;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
    Page<Party> findAllByNameContains(Pageable pageable, String name);
}
