package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.AdDTO;
import com.ekaryagin.milkcrm.dto.AdDTOext;
import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.entity.Ad;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.security.jwt.JwtTokenProvider;
import com.ekaryagin.milkcrm.service.AdService;
import com.ekaryagin.milkcrm.service.RegionService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController

public class AdController {

    private final AdService adService;
    private final Mapper mapper;
    private final UserService userService;
    private final RegionService regionService;
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public AdController(AdService adService,
                        Mapper mapper,
                        UserService userService,
                        RegionService regionService,
                        HttpServletRequest request,
                        JwtTokenProvider jwtTokenProvider) {
        this.adService = adService;
        this.mapper = mapper;
        this.userService = userService;
        this.regionService = regionService;
        this.request = request;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private User getAuthorizedUser() {
        String token = request.getHeader("authorization").substring(7);
        return userService.getUser(jwtTokenProvider.getUsername(token));
    }

    @GetMapping(value = "/ads")
    public ResponseEntity<List<AdDTO>> readAds() {

        if (getAuthorizedUser().getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Ad> ads = new ArrayList<>(adService.getAllAd());

        return !ads.isEmpty()
                ? new ResponseEntity<>(mapper.mapToAdDTOs(ads), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/ads/{id}")
    public ResponseEntity<AdDTO> readAd(@PathVariable(name = "id") long id) {

        if (adService.getAdById(id).isPresent()) {
            return new ResponseEntity<>(mapper.mapToAdDTO(adService.getAdById(id).get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/managers/[id}/ads")
    public ResponseEntity<List<AdDTO>> readAdsForManager(@PathVariable(name = "id") long id) {

        List<Ad> ads = new ArrayList<>(adService.getAdsByAuthor(userService.readManager(id)));

        return !ads.isEmpty()
                ? new ResponseEntity<>(mapper.mapToAdDTOs(ads), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/ads/actual")
    public ResponseEntity<List<AdDTO>> readActualAds() {

        List<Ad> ads = new ArrayList<>(adService.getAllAdForUser(getAuthorizedUser()));

        return !ads.isEmpty()
                ? new ResponseEntity<>(mapper.mapToAdDTOs(ads), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/regions/[id}/ads")
    public ResponseEntity<List<AdDTO>> readAdsForRegion(@PathVariable(name = "id") long id) {

        List<Ad> ads = new ArrayList<>(adService.getAllForRegion(regionService.readRegionById(id)));

        return !ads.isEmpty()
                ? new ResponseEntity<>(mapper.mapToAdDTOs(ads), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/ads")
    public ResponseEntity<String> newAd(@RequestBody AdDTOext body) {
        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        body.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return adService.saveAd(mapper.mapFromAdDTO(body))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PutMapping(value = "/ads/{id}")
    public ResponseEntity<String> saveAd(@PathVariable(name = "id") long id, @RequestBody AdDTOext body) {
        User user = getAuthorizedUser();
        if (!adService.getAdById(id).isPresent()
                || user.getRole() != Role.ADMIN
                && adService.getAdById(id).get().getAuthor() != user){
            return new ResponseEntity<>("Notification not found or insufficient access rights", HttpStatus.BAD_REQUEST);
        }

        return adService.saveAd(mapper.mapFromAdDTO(body))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(value = "/ads/{id}")
    public ResponseEntity<String> deleteAd(@PathVariable(name = "id") long id){
        User user = getAuthorizedUser();
        if (!adService.getAdById(id).isPresent()
                || user.getRole() != Role.ADMIN
                && adService.getAdById(id).get().getAuthor() != user){
            return new ResponseEntity<>("Notification not found or insufficient access rights", HttpStatus.BAD_REQUEST);
        }

        return adService.deleteAd(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
