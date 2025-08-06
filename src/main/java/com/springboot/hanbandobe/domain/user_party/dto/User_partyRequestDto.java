package com.springboot.hanbandobe.domain.user_party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User_partyRequestDto {
    private Long partyNo;

    private Long userNo;

    private boolean isParticipated;

    private boolean isHost;
}
