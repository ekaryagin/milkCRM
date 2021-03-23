package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.RegionDTOext;
import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.service.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegionController {

    private final RegionService regionService;
    private final Mapper mapper;

    public RegionController(RegionService regionService, Mapper mapper) {
        this.regionService = regionService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/regions")
    public ResponseEntity<List<RegionDTOext>> readRegions() {
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
        List<Region> regions = regionService.readRegionsByProductGroup(id);

        return regions != null &&  !regions.isEmpty()
                ? new ResponseEntity<>(mapper.mapToRegionDTOexts(regions), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/regions")
    public ResponseEntity<String> newRegion(@RequestBody RegionDTOext body){
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
        if (regionService.deleteRegion(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("This region cannot be deleted", HttpStatus.EXPECTATION_FAILED);
    }
}
