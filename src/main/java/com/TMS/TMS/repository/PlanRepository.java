package com.TMS.TMS.repository;

import com.TMS.TMS.modules.Plan;
import com.TMS.TMS.status.PlanStatus;
import com.TMS.TMS.status.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByPlanStatus(PlanStatus status);
}
