package com.springboot.hanbandobe.domain.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreferRequestDto {
    private String where;
    private String purpose;
    private String content;
}
