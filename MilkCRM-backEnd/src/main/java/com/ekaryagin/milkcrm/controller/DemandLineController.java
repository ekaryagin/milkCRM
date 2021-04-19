package com.ekaryagin.milkcrm.controller;

import com.ekaryagin.milkcrm.dto.Mapper;
import com.ekaryagin.milkcrm.dto.ProductDTO;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.security.jwt.JwtTokenProvider;
import com.ekaryagin.milkcrm.service.ProductGroupService;
import com.ekaryagin.milkcrm.service.ProductService;
import com.ekaryagin.milkcrm.service.RegionService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class DemandLineController {
    private final ProductService productService;
    private final ProductGroupService productGroupService;
    private final Mapper mapper;
    private final UserService userService;
    private final RegionService regionService;
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public DemandLineController(ProductService productService,
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

    @GetMapping(value = "/products/{id}/demand-lines")
    public ResponseEntity<ProductDTO> readDemandByProduct(@PathVariable(name = "id") long id) {
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

    @GetMapping(value = "/demands/{id}/demand-lines")


    @GetMapping(value = "/product-groups/{id}/demand-lines")
}
