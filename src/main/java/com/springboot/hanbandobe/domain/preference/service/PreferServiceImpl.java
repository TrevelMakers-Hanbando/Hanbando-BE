package com.springboot.hanbandobe.domain.preference.service;

import com.springboot.hanbandobe.domain.preference.dto.PreferRequestDto;
import com.springboot.hanbandobe.domain.preference.dto.PreferResponseDto;
import com.springboot.hanbandobe.domain.preference.repository.PreferRepository;
import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.entity.Prefer;
import com.springboot.hanbandobe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferServiceImpl implements PreferService {
    private final PreferRepository preferRepository;
    private final UserRepository userRepository;

    @Override
    public PreferResponseDto addPrefer(Long userNo, PreferRequestDto preferRequestDto) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("유저 정보를 찾을 수 없습니다."));

        Prefer prefer = Prefer.builder()
                .id(new Prefer.PreferId(user.getUserNo()))
                .user(user)
                .where(preferRequestDto.getWhere())
                .content(preferRequestDto.getContent())
                .purpose(preferRequestDto.getPurpose())
                .build();

        Prefer savedPrefer = preferRepository.save(prefer);

        return new PreferResponseDto(
                userNo,
                savedPrefer.getWhere(),
                savedPrefer.getPurpose(),
                savedPrefer.getContent(),
                savedPrefer.getCreatedAt(),
                savedPrefer.getUpdatedAt()
        );
    }


    @Override
    public PreferResponseDto getPrefer(Long userNo) {
        Prefer prefer = preferRepository.findByUser_UserNo(userNo)
                .orElseThrow(() -> new RuntimeException("해당 유저의 prefer 정보가 없습니다."));

        return new PreferResponseDto(
                userNo,
                prefer.getWhere(),
                prefer.getPurpose(),
                prefer.getContent(),
                prefer.getCreatedAt(),
                prefer.getUpdatedAt()
        );
    }

    @Override
    public void deletePrefer(Long userNo) {
        Prefer prefer = preferRepository.findByUser_UserNo(userNo)
                .orElseThrow(() -> new RuntimeException("해당 선호도가 존재하지 않습니다."));
        preferRepository.delete(prefer);
    }

    @Override
    public PreferResponseDto updatePrefer(Long userNo, PreferRequestDto preferRequestDto) {
        Prefer prefer = preferRepository.findByUser_UserNo(userNo)
                .orElseThrow(() -> new RuntimeException("해당 선호도가 존재하지 않습니다."));

        prefer.setWhere(preferRequestDto.getWhere());
        prefer.setContent(preferRequestDto.getContent());
        prefer.setPurpose(preferRequestDto.getPurpose());

        preferRepository.save(prefer);

        return new PreferResponseDto(
                userNo,
                prefer.getWhere(),
                prefer.getPurpose(),
                prefer.getContent(),
                prefer.getCreatedAt(),
                prefer.getUpdatedAt()
        );
    }

    @Override
    public PreferResponseDto patchPrefer(Long userNo, PreferRequestDto preferRequestDto) {
        Prefer prefer = preferRepository.findByUser_UserNo(userNo)
                .orElseThrow(() -> new RuntimeException("해당 선호도가 존재하지 않습니다."));

        if(preferRequestDto.getWhere() != null) prefer.setWhere(preferRequestDto.getWhere());
        if(preferRequestDto.getContent() != null) prefer.setContent(preferRequestDto.getContent());
        if(preferRequestDto.getPurpose() != null) prefer.setPurpose(preferRequestDto.getPurpose());

        Prefer preferSaved = preferRepository.save(prefer);

        return PreferResponseDto.builder()
                .userNo(preferSaved.getUser().getUserNo())
                .content(preferSaved.getContent())
                .purpose(preferSaved.getPurpose())
                .where(preferSaved.getWhere())
                .createdAt(preferSaved.getCreatedAt())
                .updatedAt(preferSaved.getUpdatedAt())
                .build();
    }
}

