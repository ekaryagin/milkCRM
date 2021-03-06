package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.SellerDTO;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.security.jwt.JwtTokenProvider;
import com.ekaryagin.milkcrm.service.RegionService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SellerController {
    private final UserService userService;
    private final RegionService regionService;
    private final Mapper mapper;
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public SellerController(UserService userService,
                            RegionService regionService,
                            Mapper mapper,
                            HttpServletRequest request,
                            JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.regionService = regionService;
        this.mapper = mapper;
        this.request = request;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private User getAuthorizedUser(){
        String token = request.getHeader("authorization").substring(7);
        return userService.getUser(jwtTokenProvider.getUsername(token));
    }

    @GetMapping(value = "/sellers")
    public ResponseEntity<List<SellerDTO>> readSellers() {
        List<Seller> sellers = userService.readAllSellers();

        return sellers != null &&  !sellers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToSellerDTOs(sellers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sellers/{id}")
    public ResponseEntity<SellerDTO> readSeller(@PathVariable(name = "id") long id) {
        Seller seller = userService.readSeller(id);

        return seller != null
                ? new ResponseEntity<>(mapper.mapToSellerDTO(seller), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sellers/retail-sellers")
    public ResponseEntity<List<SellerDTO>> readRetailSellers() {
        List<Seller> sellers = userService.readAllRetailSellers();

        return sellers != null &&  !sellers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToSellerDTOs(sellers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sellers/dealers")
    public ResponseEntity<List<SellerDTO>> readDealers() {
        List<Seller> sellers = userService.readAllDealers();

        return sellers != null &&  !sellers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToSellerDTOs(sellers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/region/{id}/sellers")
    public ResponseEntity<List<SellerDTO>> readSellersFromRegion(@PathVariable(name = "id") long id) {
        List<Seller> sellers = userService.readAllSellersFromRegion(regionService.readRegionById(id));

        return sellers != null &&  !sellers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToSellerDTOs(sellers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/region/{id}/sellers/retail-sellers")
    public ResponseEntity<List<SellerDTO>> readRetailSellersFromRegion(@PathVariable(name = "id") long id) {
        List<Seller> sellers = userService.readAllRetailSellersFromRegion(regionService.readRegionById(id));

        return sellers != null &&  !sellers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToSellerDTOs(sellers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/region/{id}/sellers/dealers")
    public ResponseEntity<List<SellerDTO>> readDealersFromRegion(@PathVariable(name = "id") long id) {
        List<Seller> sellers = userService.readAllDealersFromRegion(regionService.readRegionById(id));

        return sellers != null &&  !sellers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToSellerDTOs(sellers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/shop/{id}/sellers")
    public ResponseEntity<List<SellerDTO>> readSellersFromShop(@PathVariable(name = "id") long id) {
        List<Seller> sellers = new ArrayList<>(userService.readAllSellersFromShop(id));

        return !sellers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToSellerDTOs(sellers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/sellers")
    public ResponseEntity<String> newSeller(@RequestBody SellerDTO body){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER
                && !body.getUsername().equals(user.getUsername())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        switch (userService.addUser(mapper.mapFromSellerDTO(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("User with the same nickname already exists", HttpStatus.UNPROCESSABLE_ENTITY);
            case (2):
                return new ResponseEntity<>("Password too short", HttpStatus.BAD_REQUEST);
            case (3):
                return new ResponseEntity<>("FIO too short", HttpStatus.BAD_REQUEST);
            case (4):
                return new ResponseEntity<>("The phone number is incorrect", HttpStatus.BAD_REQUEST);
            case (5):
                return new ResponseEntity<>("The email number is incorrect", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = "/sellers")
    public ResponseEntity<String> saveSeller(@RequestBody SellerDTO body){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        switch (userService.saveUser(mapper.mapFromSellerDTO(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("User with the same nickname or id already exists," +
                        " the attempt to create a duplicate is blocked", HttpStatus.UNPROCESSABLE_ENTITY);
            case (2):
                return new ResponseEntity<>("Password too short", HttpStatus.BAD_REQUEST);
            case (3):
                return new ResponseEntity<>("FIO too short", HttpStatus.BAD_REQUEST);
            case (4):
                return new ResponseEntity<>("The phone number is incorrect", HttpStatus.BAD_REQUEST);
            case (5):
                return new ResponseEntity<>("The email number is incorrect", HttpStatus.BAD_REQUEST);
            case (6):
                return new ResponseEntity<>("The seller must have a region", HttpStatus.BAD_REQUEST);
            case (7):
                return new ResponseEntity<>("Seller not found", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/sellers/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable(name = "id") long id){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (userService.deleteSeller(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("This seller cannot be deleted", HttpStatus.EXPECTATION_FAILED);
    }


}
