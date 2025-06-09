package com.TMS.TMS.modules;

import com.TMS.TMS.status.PlanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "plan")
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String price;

    private String description;

    private Integer dataLimitMb;

    private Integer callMinutes;

    private Integer smsLimit;

    private PlanStatus planStatus = PlanStatus.PREPAID;
}
