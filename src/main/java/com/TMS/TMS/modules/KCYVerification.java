package com.TMS.TMS.modules;

import com.TMS.TMS.status.VerificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "kcyVerification")
@NoArgsConstructor
@AllArgsConstructor
public class KCYVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String documentType;

    private String documentUrl;

    private LocalDateTime verifiedAt;

    private VerificationStatus verificationStatus = VerificationStatus.PENDING;
}
