package com.TMS.TMS.servise;
;
import com.TMS.TMS.modules.KCYVerification;
import com.TMS.TMS.status.VerificationStatus;

import java.util.List;

public interface KCYVerificationService {

    KCYVerification createKCYVerification(KCYVerification kcyVerification) throws Exception;

    KCYVerification getKCYVerificationById(Long id) throws Exception;

    List<KCYVerification> getAllKCYVerification(VerificationStatus status);

    KCYVerification updateKCYVerificationStatus(Long kcyVerificationId, VerificationStatus status) throws Exception;
}
