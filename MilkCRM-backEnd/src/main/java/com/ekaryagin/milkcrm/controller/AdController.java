package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.AdDTO;
import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.entity.Ad;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.security.jwt.JwtTokenProvider;
import com.ekaryagin.milkcrm.service.AdService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController

public class AdController {

    private final AdService adService;
    private final Mapper mapper;
    private final UserService userService;
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public AdController(AdService adService,
                        Mapper mapper,
                        UserService userService,
                        HttpServletRequest request,
                        JwtTokenProvider jwtTokenProvider) {
        this.adService = adService;
        this.mapper = mapper;
        this.userService = userService;
        this.request = request;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private User getAuthorizedUser(){
        String token = request.getHeader("authorization").substring(7);
        return userService.getUser(jwtTokenProvider.getUsername(token));
    }

    @GetMapping(value = "/ads")
    public ResponseEntity<List<AdDTO>> readAds() {
        List<Ad> ads = new ArrayList<>(adService.getAllAd(getAuthorizedUser()));

        return !ads.isEmpty()
                ? new ResponseEntity<>(mapper.mapToAdDTOs(ads), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
