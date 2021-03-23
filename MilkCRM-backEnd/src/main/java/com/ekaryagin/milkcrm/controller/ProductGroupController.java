package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.ProductGroupDTO;
import com.ekaryagin.milkcrm.dto.ProductGroupDTOext;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import com.ekaryagin.milkcrm.service.ProductGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductGroupController {

    private final ProductGroupService productGroupService;
    private final Mapper mapper;

    public ProductGroupController(ProductGroupService productGroupService, Mapper mapper) {
        this.productGroupService = productGroupService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/product-groups")
    public ResponseEntity<List<ProductGroupDTO>> readProductGroups() {
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
        if (productGroupService.deleteProductGroup(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("This product group cannot be deleted", HttpStatus.EXPECTATION_FAILED);
    }
}
