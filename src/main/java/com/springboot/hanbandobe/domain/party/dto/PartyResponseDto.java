package com.springboot.hanbandobe.domain.party.dto;

import com.springboot.hanbandobe.entity.Party;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartyResponseDto {
    private Long partyNo;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PartyResponseDto(Party party) {
        this.partyNo = party.getPartyNo();
        this.name = party.getName();
        this.createdAt = party.getCreatedAt();
        this.updatedAt = party.getUpdatedAt();
    }
}
