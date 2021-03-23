package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.Ad;
import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface AdRepo extends JpaRepository<Ad, Long> {

    ArrayList<Ad> findAllByRegionsAndDisplayStartDateLessThanEqualAndDisplayEndDateGreaterThanEqual
            (Region region, Timestamp displayStartDate, Timestamp  displayEndDate);

    ArrayList<Ad> findAllByRegionsAndForSellerTrueAndDisplayStartDateLessThanEqualAndDisplayEndDateGreaterThanEqual
            (Region region, Timestamp displayStartDate, Timestamp  displayEndDate);

    ArrayList<Ad> findAllByRegionsAndForDealerTrueAndDisplayStartDateLessThanEqualAndDisplayEndDateGreaterThanEqual
            (Region regions, Timestamp displayStartDate, Timestamp  displayEndDate);

    ArrayList<Ad> findAllByAuthor(Manager manager);

}
