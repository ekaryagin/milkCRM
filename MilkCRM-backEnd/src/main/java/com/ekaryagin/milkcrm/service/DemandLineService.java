package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.DemandLine;
import com.ekaryagin.milkcrm.repository.DemandLineRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandLineService {

    public final DemandLineRepo demandLineRepo;

    public DemandLineService(DemandLineRepo demandLineRepo) {
        this.demandLineRepo = demandLineRepo;
    }

    public boolean saveDemandLine (DemandLine demandLine){
        try {
            DemandLine dml = demandLineRepo.findDemandLineByProductAndDemand(demandLine.getProduct(), demandLine.getDemand());
            if (dml != null) {
                dml.setCount(demandLine.getCount());
                demandLineRepo.save(dml);
            } else {
                demandLineRepo.save(demandLine);
            }
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public int saveAllDemandLines (List<DemandLine> dmls){
        int i = 0;
        for (DemandLine dml: dmls) {
            if (saveDemandLine(dml)){
                i++;
            }
        }
        return i;
    }
}
