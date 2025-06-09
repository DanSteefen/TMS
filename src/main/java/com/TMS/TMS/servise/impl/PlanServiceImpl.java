package com.TMS.TMS.servise.impl;

import com.TMS.TMS.exception.PlanException;
import com.TMS.TMS.modules.Plan;
import com.TMS.TMS.repository.PlanRepository;
import com.TMS.TMS.servise.PlanService;
import com.TMS.TMS.status.PlanStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Override
    public Plan createPlan(Plan plan) throws Exception {

        Plan newPlan = new Plan();
        newPlan.setName(plan.getName());
        newPlan.setPrice(plan.getPrice());
        newPlan.setDescription(plan.getDescription());
        newPlan.setDataLimitMb(plan.getDataLimitMb());
        newPlan.setCallMinutes(plan.getCallMinutes());
        newPlan.setSmsLimit(plan.getSmsLimit());
        newPlan.setPlanStatus(PlanStatus.PREPAID);

        return planRepository.save(newPlan);
    }

    @Override
    public Plan getPlanById(Long id) throws Exception {

        return planRepository.findById(id). orElseThrow(() -> new PlanException("Plan not found with this id..." + id));
    }

    @Override
    public Plan updatePlan(Long id, Plan plan) throws Exception {

        Plan existingPlan = this.getPlanById(id);

        if (plan.getName() != null){
            existingPlan.setName(plan.getName());
        }
        if (plan.getPrice() != null){
            existingPlan.setPrice(plan.getPrice());
        }
        if (plan.getDescription() != null){
            existingPlan.setDescription(plan.getDescription());
        }
        if (plan.getDataLimitMb() != null){
            existingPlan.setDataLimitMb(plan.getDataLimitMb());
        }
        if (plan.getCallMinutes() != null){
            existingPlan.setCallMinutes(plan.getCallMinutes());
        }
        if (plan.getSmsLimit() != null){
            existingPlan.setSmsLimit(plan.getSmsLimit());
        }

        return planRepository.save(existingPlan);
    }

    @Override
    public List<Plan> getAllPlan(PlanStatus status) {

        return planRepository.findByPlanStatus(status);
    }

    @Override
    public Plan updatePlanStatus(Long planId, PlanStatus status) throws Exception {

        Plan plan = getPlanById(planId);
        plan.setPlanStatus(status);
        return planRepository.save(plan);
    }
}
