package com.springboot.hanbandobe.domain.user_party.dto;

import com.springboot.hanbandobe.entity.User_party;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User_partyResponseDto {
    private Long partyNo;

    private Long userNo;

    private String userName;

    private boolean isParticipated;

    private boolean isHost;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User_partyResponseDto(User_party userParty) {
        this.partyNo = userParty.getParty().getPartyNo();
        this.userNo = userParty.getUser().getUserNo();
        this.isParticipated = userParty.getIsParticipated();
        this.isHost = userParty.getIsHost();
        this.createdAt = userParty.getCreatedAt();
        this.updatedAt = userParty.getUpdatedAt();
    }
}
