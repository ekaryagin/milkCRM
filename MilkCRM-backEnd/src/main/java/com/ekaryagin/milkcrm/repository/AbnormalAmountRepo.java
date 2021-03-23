package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.products.AbnormalAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbnormalAmountRepo extends JpaRepository<AbnormalAmount, Long> {
}
