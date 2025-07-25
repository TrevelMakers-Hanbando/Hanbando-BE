package com.springboot.hanbandobe.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_party")
public class User_party extends BaseEntity{
    @EmbeddedId
    private UserPartyId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userNo")
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("partyNo")
    @JoinColumn(name = "party_no")
    private Party party;

    @Column(name = "is_participated", nullable = false)
    private Boolean isParticipated = false;

    @Embeddable
    public static class UserPartyId implements Serializable {
        @Column(name = "user_no")
        private Long userNo;

        @Column(name = "party_no")
        private Long partyNo;
    }
}
