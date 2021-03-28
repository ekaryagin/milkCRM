package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.ProductGroupDTO;
import com.ekaryagin.milkcrm.dto.ProductGroupDTOext;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import com.ekaryagin.milkcrm.security.jwt.JwtTokenProvider;
import com.ekaryagin.milkcrm.service.ProductGroupService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProductGroupController {

    private final ProductGroupService productGroupService;
    private final Mapper mapper;
    private final UserService userService;
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public ProductGroupController(ProductGroupService productGroupService,
                                  Mapper mapper,
                                  UserService userService,
                                  HttpServletRequest request,
                                  JwtTokenProvider jwtTokenProvider) {
        this.productGroupService = productGroupService;
        this.mapper = mapper;
        this.userService = userService;
        this.request = request;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private User getAuthorizedUser(){
        String token = request.getHeader("authorization").substring(7);
        return userService.getUser(jwtTokenProvider.getUsername(token));
    }

    @GetMapping(value = "/product-groups")
    public ResponseEntity<List<ProductGroupDTO>> readProductGroups() {

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<ProductGroup> productGroups = productGroupService.getProductGroups();

        return productGroups != null && !productGroups.isEmpty()
                ? new ResponseEntity<>(mapper.mapToProductGroupDTOs(productGroups), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/product-groups/{id}")
    public ResponseEntity<ProductGroupDTO> readProductGroup(@PathVariable(name = "id") long id){
        ProductGroup productGroups = productGroupService.getProductGroup(id);
        return productGroups != null
                ? new ResponseEntity<>(mapper.mapToProductGroupDTO(productGroups), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/product-groups-ext/{id}")
    public ResponseEntity<ProductGroupDTOext> readProductGroupExt(@PathVariable(name = "id") long id){
        ProductGroup productGroups = productGroupService.getProductGroup(id);
        return productGroups != null
                ? new ResponseEntity<>(mapper.mapToProductGroupDTOext(productGroups), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/shops/{id}/product-groups")
    public ResponseEntity<List<ProductGroupDTO>> readProductGroupsByShop(@PathVariable(name = "id") long id) {
        List<ProductGroup> productGroups = productGroupService.getProductGroupByShop(id);

        return productGroups != null &&  !productGroups.isEmpty()
                ? new ResponseEntity<>(mapper.mapToProductGroupDTOs(productGroups), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/product-groups")
    public ResponseEntity<String> newProductGroup (@RequestBody ProductGroupDTOext body){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        switch (productGroupService.addNewGroup(mapper.mapFromProductGroupDTOext(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("Product group with the same title or id already exists," +
                        " the attempt to create a duplicate is blocked", HttpStatus.UNPROCESSABLE_ENTITY);
            case (2):
                return new ResponseEntity<>("Column for article or count incorrect", HttpStatus.BAD_REQUEST);
            case (3):
                return new ResponseEntity<>("The address cell is set incorrectly", HttpStatus.BAD_REQUEST);
            case (4):
                return new ResponseEntity<>("The date cell is set incorrectly", HttpStatus.BAD_REQUEST);
            case (5):
                return new ResponseEntity<>("The address and date cell must not match", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/product-groups")
    public ResponseEntity<String> saveProductGroup (@RequestBody ProductGroupDTOext body){

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        switch (productGroupService.saveGroup(mapper.mapFromProductGroupDTOext(body))){
            case (0):
                return new ResponseEntity<>(HttpStatus.OK);
            case (1):
                return new ResponseEntity<>("Product group with the same title already exists," +
                        " the attempt to create a duplicate is blocked", HttpStatus.UNPROCESSABLE_ENTITY);
            case (2):
                return new ResponseEntity<>("Column for article or count incorrect", HttpStatus.BAD_REQUEST);
            case (3):
                return new ResponseEntity<>("The address cell is set incorrectly", HttpStatus.BAD_REQUEST);
            case (4):
                return new ResponseEntity<>("The date cell is set incorrectly", HttpStatus.BAD_REQUEST);
            case (5):
                return new ResponseEntity<>("The address and date cell must not match", HttpStatus.BAD_REQUEST);
            case (7):
                return new ResponseEntity<>("Product group is not found", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Unknown error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/product-groups/{id}")
    public ResponseEntity<String> deleteProductGroup (@PathVariable(name = "id") long id) {

        User user = getAuthorizedUser();
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (productGroupService.deleteProductGroup(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("This product group cannot be deleted", HttpStatus.EXPECTATION_FAILED);
    }
}
