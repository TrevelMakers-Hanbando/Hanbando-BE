package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.user_party.service.User_partyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user_party")
@Tag(name = "User_party", description = "파티 구성원 관련 API")
public class User_partyController {
    private final User_partyService userPartyService;


}
