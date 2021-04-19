package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.ProductDTO;
import com.ekaryagin.milkcrm.dto.ProductDTOext;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.security.jwt.JwtTokenProvider;
import com.ekaryagin.milkcrm.service.ProductGroupService;
import com.ekaryagin.milkcrm.service.ProductService;
import com.ekaryagin.milkcrm.service.RegionService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductGroupService productGroupService;
    private final Mapper mapper;
    private final UserService userService;
    private final RegionService regionService;
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public ProductController(ProductService productService,
                             ProductGroupService productGroupService,
                             Mapper mapper,
                             UserService userService,
                             RegionService regionService,
                             HttpServletRequest request,
                             JwtTokenProvider jwtTokenProvider) {
        this.productService = productService;
        this.productGroupService = productGroupService;
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

    @GetMapping(value = "/products/{id}/for-sellers")
    public ResponseEntity<ProductDTO> readProduct(@PathVariable(name = "id") long id) {
        Optional<Product> product = productService.getProduct(id);
        User user = getAuthorizedUser();
        if (user.getRole() == Role.RETAIL_SELLER || user.getRole() == Role.DEALER) {
            if (product.isPresent()) {
                return new ResponseEntity<>(mapper.mapToProductDTO(product.get(),
                                            user.getRegions().stream().findFirst().get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/managers/{id}/products")
    public ResponseEntity<List<ProductDTOext>> readProductByAuthor(@PathVariable(name = "id") long id) {
        HashSet<Product> products = new HashSet<>(productService.getProductByAuthor(userService.readManager(id)));
        if (!products.isEmpty()){
            return new ResponseEntity<>(mapper.mapToProductDTOexts(products), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/product-groups/{productGroupId}/regions/{regionID}/products")
    public ResponseEntity<List<ProductDTO>> readProductsByProductGroup
                                            (@PathVariable(name = "productGroupId") long groupId,
                                             @PathVariable(name = "regionID") long regionId){
        ArrayList<Product> products = new ArrayList<>(
                productService.getProductByProductGroup(productGroupService.getProductGroup(groupId)));
        if (!products.isEmpty()){
            return new ResponseEntity<>(mapper.mapToProductDTOs(products, regionService.readRegionById(regionId)),
                                        HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<String> addProduct (@RequestBody ProductDTOext body){
        return productService.addNewProduct(mapper.mapFromProductDTOext(body))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>("New product not saved",HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/product-groups/{id}/products/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProductsFromExcel(@RequestParam("file") MultipartFile file, @PathVariable(name = "id") long id){

        if (getAuthorizedUser().getRole() != Role.MANAGER){
            return new ResponseEntity<>("Access denied",HttpStatus.FORBIDDEN);
        }
        Map<String, Boolean> answer = productService.addNewProductsFromExcel(productGroupService.getProductGroup(id),
                                                (Manager) getAuthorizedUser(), file);
        return new ResponseEntity<>(answer.toString()+" "+answer.size(), HttpStatus.OK);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<String> saveProduct (@PathVariable(name = "id") long id,
                                               @RequestBody ProductDTOext body){
        if (body.getId() == id){
            return productService.addNewProduct(mapper.mapFromProductDTOext(body))
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>("Product not saved",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product not saved", HttpStatus.BAD_REQUEST);
    }
}
