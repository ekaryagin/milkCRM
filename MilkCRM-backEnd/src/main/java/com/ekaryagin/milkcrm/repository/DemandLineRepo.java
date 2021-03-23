package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.Demand;
import com.ekaryagin.milkcrm.entity.DemandLine;
import com.ekaryagin.milkcrm.entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DemandLineRepo  extends JpaRepository<DemandLine, Long> {

    ArrayList<DemandLine> findAllByProduct(Product product);

    ArrayList<DemandLine> findAllByDemand(Demand demand);

    DemandLine findDemandLineByProductAndDemand(Product product, Demand demand);
}
