package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.ManagerDTO;
import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.entity.employee.Manager;
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
public class ManagerController {

    private final UserService userService;
    private final RegionService regionService;
    private final Mapper mapper;
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public ManagerController(UserService userService,
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

    @GetMapping(value = "/admins")
    public ResponseEntity<List<ManagerDTO>> readAdmins() {

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Manager> admins = userService.readAllAdmins();

        return admins != null &&  !admins.isEmpty()
                ? new ResponseEntity<>(mapper.mapToManagerDTOs(admins), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/managers")
    public ResponseEntity<List<ManagerDTO>> readManagers() {

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Manager> managers = userService.readAllManagers();

        return managers != null &&  !managers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToManagerDTOs(managers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/regions/{id}/managers")
    public ResponseEntity<List<ManagerDTO>> readMangersFromRegion(@PathVariable(name = "id") long id){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Manager> managers = userService.readAllMangersFromRegion(regionService.readRegionById(id));

        return managers != null && !managers.isEmpty()
                ? new ResponseEntity<>(mapper.mapToManagerDTOs(managers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/managers/{id}")
    public ResponseEntity<ManagerDTO> readManager(@PathVariable(name = "id") long id){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Manager manager = userService.readManager(id);

        return manager != null
                ? new ResponseEntity<>(mapper.mapToManagerDTO(manager), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="/managers")
    public ResponseEntity<String> newManager(@RequestBody ManagerDTO body){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (userService.deleteManager(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("This manager cannot be deleted", HttpStatus.EXPECTATION_FAILED);
    }
}
