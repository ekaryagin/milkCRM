package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.ShopDTO;
import com.ekaryagin.milkcrm.entity.Shop;
import com.ekaryagin.milkcrm.service.ProductGroupService;
import com.ekaryagin.milkcrm.service.RegionService;
import com.ekaryagin.milkcrm.service.ShopService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShopController {

    private final ShopService shopService;
    private final RegionService regionService;
    private final UserService userService;
    private final ProductGroupService productGroupService;
    private final Mapper mapper;

    public ShopController(ShopService shopService, RegionService regionService,
                          UserService userService, ProductGroupService productGroupService, Mapper mapper) {
        this.shopService = shopService;
        this.regionService = regionService;
        this.userService = userService;
        this.productGroupService = productGroupService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/shops")
    public ResponseEntity<List<ShopDTO>> readShops() {
        List<Shop> shops = shopService.getShops();

        return shops != null && !shops.isEmpty()
                ? new ResponseEntity<>(mapper.mapToShopDTOs(shops), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/shop/{id}")
    public ResponseEntity<ShopDTO> readShop(@PathVariable(name = "id") long id) {
        Shop shop = shopService.getShopById(id);

        return shop != null
                ? new ResponseEntity<>(mapper.mapToShopDTO(shop), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/regions/{id}/shops")
    public ResponseEntity<List<ShopDTO>> readShopsByRegion(@PathVariable(name = "id") long id) {
        List<Shop> shops = shopService.getShopsByRegions(regionService.readRegionById(id));

        return shops != null && !shops.isEmpty()
                ? new ResponseEntity<>(mapper.mapToShopDTOs(shops), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sellers/{id}/shops")
    public ResponseEntity<List<ShopDTO>> readShopsBySeller(@PathVariable(name = "id") long id) {
        List<Shop> shops = new ArrayList<>(userService.readSeller(id).getShops());

        return !shops.isEmpty()
                ? new ResponseEntity<>(mapper.mapToShopDTOs(shops), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/managers/{id}/shops")
    public ResponseEntity<List<ShopDTO>> readShopsByManager(@PathVariable(name = "id") long id) {
        List<Shop> shops = new ArrayList<>();
        userService.readManager(id).getRegions().forEach(region -> shops.addAll(region.getShops()));

        return !shops.isEmpty()
                ? new ResponseEntity<>(mapper.mapToShopDTOs(shops), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/product-groups/{id}/shops")
    public ResponseEntity<List<ShopDTO>> readShopsByProductGroup(@PathVariable(name = "id") long id) {
        List<Shop> shops = shopService.getShopsByProductGroup(productGroupService.getProductGroup(id));

        return !shops.isEmpty()
                ? new ResponseEntity<>(mapper.mapToShopDTOs(shops), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/shops")
    public ResponseEntity<String> newShop (@RequestBody ShopDTO body){

        switch (shopService.addShop(mapper.mapFromShopDTO(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("Shop with the same address or id already exists," +
                        " the attempt to create a duplicate is blocked", HttpStatus.UNPROCESSABLE_ENTITY);
            case (2):
                return new ResponseEntity<>("You can't create a shop without setting a region", HttpStatus.BAD_REQUEST);
            case (3):
                return new ResponseEntity<>("Please fill in all the fields", HttpStatus.BAD_REQUEST);
            case (4):
                return new ResponseEntity<>("The shop must have sellers", HttpStatus.BAD_REQUEST);
            case (5):
                return new ResponseEntity<>("The shop must have product group", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/shops")
    public ResponseEntity<String> saveShop (@RequestBody ShopDTO body){

        switch (shopService.saveShop(mapper.mapFromShopDTO(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("Shop with the same address or id already exists," +
                        " the attempt to create a duplicate is blocked", HttpStatus.UNPROCESSABLE_ENTITY);
            case (2):
                return new ResponseEntity<>("You can't create a shop without setting a region", HttpStatus.BAD_REQUEST);
            case (3):
                return new ResponseEntity<>("Please fill in all the fields", HttpStatus.BAD_REQUEST);
            case (4):
                return new ResponseEntity<>("The shop must have sellers", HttpStatus.BAD_REQUEST);
            case (5):
                return new ResponseEntity<>("The shop must have product group", HttpStatus.BAD_REQUEST);
            case (7):
                return new ResponseEntity<>("The shop is not found", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/shops/{id}")
    public ResponseEntity<String> deleteShop (@PathVariable(name = "id") long id){
        if (shopService.deleteShop(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("This shop cannot be deleted", HttpStatus.EXPECTATION_FAILED);
    }
}
