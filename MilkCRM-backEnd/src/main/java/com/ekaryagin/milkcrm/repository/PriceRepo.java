package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.products.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepo extends JpaRepository<Price, Long> {
}
