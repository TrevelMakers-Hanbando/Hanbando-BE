package com.springboot.hanbandobe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prefer")
public class Prefer extends BaseEntity {
    @EmbeddedId
    private PreferId id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("userNo")
    @JoinColumn(name = "user_no")
    private User user;

    @Column(name = "`where`", nullable = false, columnDefinition = "TEXT")
    private String where;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "purpose", nullable = false, columnDefinition = "TEXT")
    private String purpose;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Embeddable
    public static class PreferId implements Serializable {
        @Column(name = "user_no")
        private Long userNo;
    }
}
