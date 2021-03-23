package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.Ad;
import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.Shop;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.products.*;
import com.ekaryagin.milkcrm.service.ProductGroupService;
import com.ekaryagin.milkcrm.service.RegionService;
import com.ekaryagin.milkcrm.service.ShopService;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Mapper {

    private final RegionService regionService;
    private final ProductGroupService productGroupService;
    private final ShopService shopService;
    private final UserService userService;

    public Mapper(RegionService regionService, ProductGroupService productGroupService, ShopService shopService, UserService userService) {
        this.regionService = regionService;
        this.productGroupService = productGroupService;
        this.shopService = shopService;
        this.userService = userService;
    }

//
//
//
//    Shops
//
//
//
//

    public ShopDTO mapToShopDTO(Shop shop){
        ShopDTO shopDTO = new ShopDTO();

        shopDTO.setId(shop.getId());
        shopDTO.setActive(shop.isActive());
        shopDTO.setRegion(shop.getRegion());
        shopDTO.setAddress(shop.getAddress());
        shopDTO.setDescription(shop.getDescription());
        shopDTO.setLegalEntity(shop.getLegalEntity());

        if (!shop.getProductGroups().isEmpty()) {
            List<ProductGroupDTO> productGroupsDTO = new ArrayList<>();
            shop.getProductGroups().forEach(group
                    -> productGroupsDTO.add(mapToProductGroupDTO(productGroupService.getProductGroup(group.getId()))));
            shopDTO.setProductGroups(productGroupsDTO);
        }

        if (!shop.getSellers().isEmpty()) {
            List<SellerDTO> sellersDTO = new ArrayList<>();
            shop.getSellers().forEach(seller
                    -> sellersDTO.add(mapToSellerDTO(userService.readSeller(seller.getId()))));
            shopDTO.setSellers(sellersDTO);
        }

        return shopDTO;
    }

    public List<ShopDTO> mapToShopDTOs(List<Shop> shops){
        List<ShopDTO> shopsDTO = new ArrayList<>();

        for (Shop shop: shops) {
            shopsDTO.add(mapToShopDTO(shop));
        }

        return shopsDTO;
    }

    public Shop mapFromShopDTO(ShopDTO shopDTO){
        Shop shop = new Shop();

        shop.setId(shopDTO.getId());
        shop.setActive(shopDTO.isActive());
        shop.setRegion(shopDTO.getRegion());

        if (shopDTO.getSellers() != null) {
            HashSet<Seller> sellers = new HashSet<>();
            shopDTO.getSellers().forEach(sellerDTO
                    -> sellers.add(userService.readSeller(sellerDTO.getId())));
            shop.setSellers(sellers);
        }

        if (shopDTO.getProductGroups() != null) {
            HashSet<ProductGroup> productGroups = new HashSet<>();
            shopDTO.getProductGroups().forEach(groupDTO
                    -> productGroups.add(productGroupService.getProductGroup(groupDTO.getId())));
            shop.setProductGroups(productGroups);
        }

        return shop;
    }

//
//
//
//    Sellers
//
//
//
//

    public List<ShopDTO> mapToSellerDTOs(Set<Shop> shops){
        List<ShopDTO> shopsDTO = new ArrayList<>();

        for (Shop shop: shops) {
            shopsDTO.add(mapToShopDTO(shop));
        }

        return shopsDTO;
    }

    public SellerDTO mapToSellerDTO(Seller seller){
        SellerDTO dto = new SellerDTO();

        dto.setId(seller.getId());
        dto.setNic(seller.getNic());
        dto.setFio(seller.getFio());
        dto.setEmail(seller.getEmail());
        dto.setPhoneNumber(seller.getPhoneNumber());
        dto.setRole(seller.getRole());
        dto.setActive(seller.isActive());

        if (!seller.getRegions().isEmpty()) {
            dto.setRegion(mapToRegionDTO((new ArrayList<>(seller.getRegions()).get(0))));
        }

        dto.setShops(mapToSellerDTOs(seller.getShops()));
        dto.setStrictRule(seller.isStrictRule());

        return dto;
    }

    public List<SellerDTO> mapToSellerDTOs(List<Seller> sellers){
        List<SellerDTO> sellersDTO = new ArrayList<>();

        for (Seller seller: sellers) {
            sellersDTO.add(mapToSellerDTO(seller));
        }

        return sellersDTO;
    }

    public Seller mapFromSellerDTO(SellerDTO sellerDTO){

        Seller seller = new Seller();

        seller.setId(sellerDTO.getId());
        seller.setNic(sellerDTO.getNic());
        seller.setFio(sellerDTO.getFio());
        seller.setEmail(sellerDTO.getEmail());
        seller.setPhoneNumber(sellerDTO.getPhoneNumber());
        seller.setPassword(sellerDTO.getPassword());
        seller.setRole(sellerDTO.getRole());
        seller.setActive(sellerDTO.isActive());

        if (sellerDTO.getRegion() != null) {
            HashSet<Region> regions = new HashSet<>();
            regions.add(regionService.readRegionById(sellerDTO.getRegion().getId()));
            seller.setRegions(regions);
        }

        if (sellerDTO.getShops() != null) {
            HashSet<Shop> shops = new HashSet<>();
            sellerDTO.getShops().forEach(shopDTO -> shops.add(shopService.getShopById(shopDTO.getId())));
            seller.setShops(shops);
        }

        seller.setStrictRule(sellerDTO.isStrictRule());

        return seller;
    }

//
//
//
//    Managers
//
//
//
//

    public ManagerDTO mapToManagerDTO (Manager manager){
        ManagerDTO dto = new ManagerDTO();

        dto.setId(manager.getId());
        dto.setNic(manager.getNic());
        dto.setFio(manager.getFio());
        dto.setEmail(manager.getEmail());
        dto.setPhoneNumber(manager.getPhoneNumber());
        dto.setRole(manager.getRole());
        dto.setActive(manager.isActive());
        dto.setPassword(manager.getPassword());
        dto.setProductGroups(mapToProductGroupDTOs(manager.getProductGroups()));
        dto.setRegions(mapToRegionDTOs((List)manager.getRegions()));

        return dto;
    }

    public List<ManagerDTO> mapToManagerDTOs (List<Manager> managers){
        List<ManagerDTO> managersDTO = new ArrayList<>();

        for (Manager manager: managers) {
            managersDTO.add(mapToManagerDTO(manager));
        }

        return managersDTO;
    }

    public List<ManagerDTO> mapToManagerDTOs (Set<Manager> managers){
        List<ManagerDTO> managersDTO = new ArrayList<>();

        for (Manager manager: managers) {
            managersDTO.add(mapToManagerDTO(manager));
        }

        return managersDTO;
    }

    public Manager mapFromManagerDTO(ManagerDTO managerDTO){
        Manager manager = new Manager();

        manager.setNic(managerDTO.getNic());
        manager.setFio(managerDTO.getFio());
        manager.setEmail(managerDTO.getEmail());
        manager.setPhoneNumber(managerDTO.getPhoneNumber());
        manager.setPassword(managerDTO.getPassword());
        manager.setRole(managerDTO.getRole());
        manager.setActive(managerDTO.isActive());

        if (managerDTO.getRegions() != null) {
            HashSet<Region> regions = new HashSet<>();
            managerDTO.getRegions().forEach(regionDTO -> regions.add(regionService.readRegionById(regionDTO.getId())));
            manager.setRegions(regions);
        }

        if (managerDTO.getProductGroups() != null) {
            HashSet<ProductGroup> productGroups = new HashSet<>();
            managerDTO.getProductGroups().forEach(groupDTO -> productGroups.add(productGroupService.getProductGroup(groupDTO.getId())));
            manager.setProductGroups(productGroups);
        }

        return manager;
    }

//
//
//
//    ProductGroup
//
//
//
//

    public ProductGroupDTO mapToProductGroupDTO (ProductGroup productGroup){
        ProductGroupDTO dto = new ProductGroupDTO();

        dto.setId(productGroup.getId());
        dto.setTitle(productGroup.getTitle());
        dto.setVendor(productGroup.getVendor());
        dto.setOwner(mapToManagerDTOs(productGroup.getOwner()));

        return dto;
    }

    public ProductGroupDTOext mapToProductGroupDTOext (ProductGroup productGroup){
        ProductGroupDTOext dto = new ProductGroupDTOext();

        dto.setId(productGroup.getId());
        dto.setTitle(productGroup.getTitle());
        dto.setVendor(productGroup.getVendor());
        dto.setOwner(mapToManagerDTOs(productGroup.getOwner()));

        dto.setArticleColumn(productGroup.getArticleColumn());
        dto.setCountColumn(productGroup.getCountColumn());
        dto.setSheetName(productGroup.getSheetName());
        dto.setAddressRow(productGroup.getAddressRow());
        dto.setAddressCell(productGroup.getAddressCell());
        dto.setDateRow(productGroup.getDateRow());
        dto.setDateCell(productGroup.getDateCell());
        dto.setTitleForFile(productGroup.getTitleForFile());

        return dto;
    }

    public List<ProductGroupDTO> mapToProductGroupDTOs (List<ProductGroup> productGroups){
        List<ProductGroupDTO> productGroupsDTO = new ArrayList<>();

        for (ProductGroup group: productGroups) {
            productGroupsDTO.add(mapToProductGroupDTO(group));
        }

        return productGroupsDTO;
    }

    public List<ProductGroupDTOext> mapToProductGroupDTOexts (List<ProductGroup> productGroups){
        List<ProductGroupDTOext> productGroupsDTO = new ArrayList<>();

        for (ProductGroup group: productGroups) {
            productGroupsDTO.add(mapToProductGroupDTOext(group));
        }

        return productGroupsDTO;
    }

    public List<ProductGroupDTO> mapToProductGroupDTOs (Set<ProductGroup> productGroups){
        List<ProductGroupDTO> productGroupsDTO = new ArrayList<>();

        for (ProductGroup group: productGroups) {
            productGroupsDTO.add(mapToProductGroupDTO(group));
        }

        return productGroupsDTO;
    }

    public ProductGroup mapFromProductGroupDTO (ProductGroupDTO productGroupDTO){
        ProductGroup group = new ProductGroup();

        group.setId(productGroupDTO.getId());
        group.setTitle(productGroupDTO.getTitle());
        group.setVendor(productGroupDTO.getVendor());
        group.setOwner((Set)productGroupDTO.getOwner());

        return group;
    }

    public ProductGroup mapFromProductGroupDTOext (ProductGroupDTOext productGroupDTOext){
        ProductGroup group = new ProductGroup();

        group.setId(productGroupDTOext.getId());
        group.setTitle(productGroupDTOext.getTitle());
        group.setVendor(productGroupDTOext.getVendor());
        group.setOwner((Set)productGroupDTOext.getOwner());

        group.setArticleColumn(productGroupDTOext.getArticleColumn());
        group.setCountColumn(productGroupDTOext.getCountColumn());
        group.setSheetName(productGroupDTOext.getSheetName());
        group.setAddressRow(productGroupDTOext.getAddressRow());
        group.setAddressCell(productGroupDTOext.getAddressCell());
        group.setDateRow(productGroupDTOext.getDateRow());
        group.setDateCell(productGroupDTOext.getDateCell());
        group.setTitleForFile(productGroupDTOext.getTitleForFile());

        return group;
    }

//
//
//
//    Product
//
//
//
//

    public ProductDTOext mapToProductDTOext (Product product) {
        ProductDTOext dto = new ProductDTOext();

        dto.setId(product.getId());
        dto.setArticle(product.getArticle());
        dto.setTitle(product.getTitle());
        dto.setMeasureUnit(product.getMeasureUnit());
        dto.setMainPrice(product.getMainPrice());
        dto.setMainKvant(product.getMainKvant());
        dto.setMainAbnormalAmount(product.getMainAbnormalAmount());
        dto.setPrice((List<Price>) product.getPrice());
        dto.setKvant((List<Kvant>) product.getKvant());
        dto.setAbnormalAmount((List<AbnormalAmount>) product.getAbnormalAmount());

        return dto;
    }

    public Set<ProductDTOext> mapToProductDTOexts (Set<Product> products) {
        Set<ProductDTOext> productsDTO = new HashSet<>();

        for (Product product : products) {
            productsDTO.add(mapToProductDTOext(product));
        }

        return productsDTO;
    }

    public ProductDTO mapToProductDTO (Product product, Region region) {
        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setArticle(product.getArticle());
        dto.setTitle(product.getTitle());
        dto.setMeasureUnit(product.getMeasureUnit());

        dto.setPrice(
                product.getPrice().stream().filter(price1 -> price1.getRegion() == region)
                        .findFirst().map(Price::getValue).orElseGet(product::getMainPrice)
        );
        dto.setKvant(
                product.getKvant().stream().filter(kvant1 -> kvant1.getRegion() == region)
                        .findFirst().map(Kvant::getValue).orElseGet(product::getMainKvant)
        );
        dto.setAbnormalAmount(
                product.getAbnormalAmount().stream().filter(abnormal -> abnormal.getRegion() == region)
                        .findFirst().map(AbnormalAmount::getValue).orElseGet(product::getMainAbnormalAmount)
        );

        return dto;
    }

    public List<ProductDTO> mapToProductDTOs (List<Product> products, Region region){
        List<ProductDTO> adsDTO = new ArrayList<>();

        for (Product product: products) {
            adsDTO.add(mapToProductDTO(product, region));
        }

        return adsDTO;
    }

//
//
//
//    Region
//
//
//
//

    public RegionDTOext mapToRegionDTOext(Region region){
        RegionDTOext dto = new RegionDTOext();

        dto.setId(region.getId());
        dto.setTitle(region.getTitle());
        dto.setActive(region.isActive());
        dto.setProductGroups(mapToProductGroupDTOs(region.getProducts()));

        return dto;
    }

    public List<RegionDTOext> mapToRegionDTOexts(List<Region> regions){
        List<RegionDTOext> regionsDTO = new ArrayList<>();

        for (Region region: regions) {
            regionsDTO.add(mapToRegionDTOext(region));
        }

        return regionsDTO;
    }

    public Region mapFromRegionDTOext (RegionDTOext regionDTOext){
        Region region = new Region();
        region.setId(regionDTOext.getId());
        region.setTitle(regionDTOext.getTitle());
        region.setActive(regionDTOext.isActive());

        if(regionDTOext.getProductGroups() != null){
            HashSet<ProductGroup> productGroups = new HashSet<>();
            regionDTOext.getProductGroups().forEach(productGroupDTO
                    -> productGroups.add(productGroupService.getProductGroup(productGroupDTO.getId())));
            region.setProducts(productGroups);
        }

        if (regionDTOext.getShops() != null){
            HashSet<Shop> shops = new HashSet<>();
            regionDTOext.getShops().forEach(shopDTO
                    -> shops.add(shopService.getShopById(shopDTO.getId())));
            region.setShops(shops);
        }
        return region;
    }

    public RegionDTO mapToRegionDTO(Region region){
        RegionDTO dto = new RegionDTO();

        dto.setId(region.getId());
        dto.setTitle(region.getTitle());

        return dto;
    }

    public List<RegionDTO> mapToRegionDTOs(List<Region> regions){
        List<RegionDTO> regionsDTO = new ArrayList<>();

        for (Region region: regions) {
            regionsDTO.add(mapToRegionDTO(region));
        }

        return regionsDTO;
    }

//
//
//
//    Ad
//
//
//
//

    public AdDTO mapToAdDTO(Ad ad){
        AdDTO dto = new AdDTO();

        dto.setId(ad.getId());
        dto.setAuthor(ad.getAuthor());
        dto.setTitle(ad.getTitle());
        dto.setText(ad.getText());
        dto.setCreationDate(ad.getCreationDate());

        return dto;
    }

    public List<AdDTO> mapToAdDTOs (List <Ad> ads){
        List<AdDTO> adsDTO = new ArrayList<>();

        for (Ad ad: ads) {
            adsDTO.add(mapToAdDTO(ad));
        }

        return adsDTO;
    }

    public AdDTOext mapToManagerDTOext (Ad ad){
        AdDTOext dto = new AdDTOext();

        dto.setId(ad.getId());
        dto.setAuthor(ad.getAuthor());
        dto.setTitle(ad.getTitle());
        dto.setText(ad.getText());
        dto.setCreationDate(ad.getCreationDate());
        dto.setDisplayStartDate(ad.getDisplayStartDate());
        dto.setDisplayEndDate(ad.getDisplayEndDate());
        dto.setRegions(ad.getRegions());
        dto.setForSeller(ad.isForSeller());
        dto.setForDealer(ad.isForDealer());

        return dto;
    }

    public Set<AdDTOext> mapToManagerDTOexts (Set<Ad> ads){
        Set<AdDTOext> adsDTOext = new HashSet<>();

        for (Ad ad: ads) {
            adsDTOext.add(mapToManagerDTOext(ad));
        }

        return adsDTOext;
    }
}
