package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.ManagerDTO;
import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.service.RegionService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {
    private final UserService userService;
    private final RegionService regionService;
    private final Mapper mapper;

    public ManagerController(UserService userService, RegionService regionService, Mapper mapper) {
        this.userService = userService;
        this.regionService = regionService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/admins")
    public ResponseEntity<List<ManagerDTO>> readAdmins() {
        List<Manager> admins = userService.readAllAdmins();

        return admins != null &&  !admins.isEmpty()
                ? new ResponseEntity<>(mapper.mapToManagerDTOs(admins), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/managers")
    public ResponseEntity<List<ManagerDTO>> readManagers() {
        List<Manager> managers = userService.readAllManagers();

        return managers != null &&  !managers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToManagerDTOs(managers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/regions/{id}/managers")
    public ResponseEntity<List<ManagerDTO>> readMangersFromRegion(@PathVariable(name = "id") long id){
        List<Manager> managers = userService.readAllMangersFromRegion(regionService.readRegionById(id));

        return managers != null && !managers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToManagerDTOs(managers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/managers/{id}")
    public ResponseEntity<ManagerDTO> readManager(@PathVariable(name = "id") long id){
        Manager manager = userService.readManager(id);

        return manager != null
                ? new ResponseEntity<>(mapper.mapToManagerDTO(manager), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="/managers")
    public ResponseEntity<String> newManager(@RequestBody ManagerDTO body){

        switch (userService.addUser(mapper.mapFromManagerDTO(body))){
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
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/managers")
    public ResponseEntity<String> saveManager(@RequestBody ManagerDTO body){

        switch (userService.saveUser(mapper.mapFromManagerDTO(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("User with the same nickname already exists," +
                        " the attempt to create a duplicate is blocked", HttpStatus.UNPROCESSABLE_ENTITY);
            case (2):
                return new ResponseEntity<>("Password too short", HttpStatus.BAD_REQUEST);
            case (3):
                return new ResponseEntity<>("FIO too short", HttpStatus.BAD_REQUEST);
            case (4):
                return new ResponseEntity<>("The phone number is incorrect", HttpStatus.BAD_REQUEST);
            case (5):
                return new ResponseEntity<>("The email number is incorrect", HttpStatus.BAD_REQUEST);
            case (7):
                return new ResponseEntity<>("Manager not found", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/managers/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable(name = "id") long id) {
        if (userService.deleteManager(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("This manager cannot be deleted", HttpStatus.EXPECTATION_FAILED);
    }
}
