package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.RegionDTOext;
import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.security.jwt.JwtTokenProvider;
import com.ekaryagin.milkcrm.service.RegionService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RegionController {

    private final RegionService regionService;
    private final Mapper mapper;
    private final UserService userService;
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public RegionController(RegionService regionService,
                            Mapper mapper,
                            UserService userService,
                            HttpServletRequest request,
                            JwtTokenProvider jwtTokenProvider) {
        this.regionService = regionService;
        this.mapper = mapper;
        this.userService = userService;
        this.request = request;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private User getAuthorizedUser(){
        String token = request.getHeader("authorization").substring(7);
        return userService.getUser(jwtTokenProvider.getUsername(token));
    }

    @GetMapping(value = "/regions")
    public ResponseEntity<List<RegionDTOext>> readRegions() {

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Region> regions = regionService.readRegions();

        return regions != null &&  !regions.isEmpty()
                ? new ResponseEntity<>(mapper.mapToRegionDTOexts(regions), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/regions/{id}")
    public ResponseEntity<RegionDTOext> readRegion(@PathVariable(name = "id") long id){
        Region region = regionService.readRegionById(id);
        return region != null
                ? new ResponseEntity<>(mapper.mapToRegionDTOext(region), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/product-groups/{id}/regions")
    public ResponseEntity<List<RegionDTOext>> readRegionsByProductGroup(@PathVariable(name = "id") long id){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Region> regions = regionService.readRegionsByProductGroup(id);

        return regions != null &&  !regions.isEmpty()
                ? new ResponseEntity<>(mapper.mapToRegionDTOexts(regions), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/regions")
    public ResponseEntity<String> newRegion(@RequestBody RegionDTOext body){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        switch (regionService.addRegion(mapper.mapFromRegionDTOext(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("Region with the same title or id already exists," +
                        " the attempt to create a duplicate is blocked", HttpStatus.UNPROCESSABLE_ENTITY);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/regions")
    public ResponseEntity<String> saveRegion (@RequestBody RegionDTOext body){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        switch (regionService.saveRegion(mapper.mapFromRegionDTOext(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("Region with the same title already exists," +
                        " the attempt to create a duplicate is blocked", HttpStatus.UNPROCESSABLE_ENTITY);
            case (7):
                return new ResponseEntity<>("Region not found", HttpStatus.UNPROCESSABLE_ENTITY);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(value = "/regions/{id}")
    public ResponseEntity<String> deleteRegion (@PathVariable(name = "id") long id) {

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (regionService.deleteRegion(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("This region cannot be deleted", HttpStatus.EXPECTATION_FAILED);
    }
}
