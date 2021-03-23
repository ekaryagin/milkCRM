package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.AdDTO;
import com.ekaryagin.milkcrm.entity.Ad;
import com.ekaryagin.milkcrm.service.AdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdController {

    private final AdService adService;
    private final Mapper mapper;

    public AdController(AdService adService, Mapper mapper) {
        this.adService = adService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/ads")
    public ResponseEntity<List<AdDTO>> readAds() {
        List<Ad> ads = new ArrayList<>();
/////////////////////////////   ПОПРАВИТЬ ЭТОТ МЕТОД НИЧЕГО НЕ ВЫДААЕТ ////////////////////////////////////////
//   ads.addAll(adService.getAllAd(ВПИСАТЬ СЮДА ЗАПРРОСИВШЕГО ЮЗЕРА));
        return ads != null && !ads.isEmpty()
                ? new ResponseEntity<>(mapper.mapToAdDTOs(ads), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
