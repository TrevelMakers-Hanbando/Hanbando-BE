package com.springboot.hanbandobe.domain.preference.service;

import com.springboot.hanbandobe.domain.preference.dto.PreferRequestDto;
import com.springboot.hanbandobe.domain.preference.dto.PreferResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

public interface PreferService {
    PreferResponseDto addPrefer(Long userNo, @Valid PreferRequestDto preferRequestDto);

    PreferResponseDto getPrefer(Long userNo);

    void deletePrefer(Long userNo);

    PreferResponseDto updatePrefer(Long userNo, @Valid PreferRequestDto preferRequestDto);

    PreferResponseDto patchPrefer(Long userNo, @Valid PreferRequestDto preferRequestDto);
}
