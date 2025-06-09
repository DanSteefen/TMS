package com.TMS.TMS.servise;

import com.TMS.TMS.modules.Plan;
import com.TMS.TMS.status.PlanStatus;

import java.util.List;

public interface PlanService {

    Plan createPlan(Plan plan) throws Exception;

    Plan getPlanById(Long id) throws Exception;

    Plan updatePlan(Long id, Plan plan) throws Exception;

    List<Plan> getAllPlan(PlanStatus status);

    Plan updatePlanStatus(Long planId, PlanStatus status) throws Exception;
}
