package com.TMS.TMS.controller;

import com.TMS.TMS.modules.Plan;
import com.TMS.TMS.servise.PlanService;
import com.TMS.TMS.status.PlanStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping("/createPlan")
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) throws Exception{

        Plan newPlan = planService.createPlan(plan);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) throws Exception {

        Plan plan = planService.getPlanById(id);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @PatchMapping("/plan/{id}")
    public ResponseEntity<Plan> updatePlan( @RequestBody Plan plan) throws Exception {

        Plan updatePlan = planService.updatePlan(plan.getId(), plan);
        return  ResponseEntity.ok(updatePlan);
    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlan(@RequestParam(required = false) PlanStatus status){

        List<Plan> plans = planService.getAllPlan(status);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
