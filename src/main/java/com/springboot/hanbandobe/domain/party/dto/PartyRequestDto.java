package com.springboot.hanbandobe.domain.party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartyRequestDto {
    private Long partyNo;

    private String name;
}
