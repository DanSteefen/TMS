package com.TMS.TMS.repository;

import com.TMS.TMS.modules.KCYVerification;
import com.TMS.TMS.status.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KCYVerificationRepository extends JpaRepository<KCYVerification, Long> {

    List<KCYVerification> findByKCYVerificationStatus(VerificationStatus status);
}
