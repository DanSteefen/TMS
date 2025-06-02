package com.TMS.TMS.servise.impl;

import com.TMS.TMS.modules.KCYVerification;
import com.TMS.TMS.modules.User;
import com.TMS.TMS.repository.KCYVerificationRepository;
import com.TMS.TMS.repository.UserRepository;
import com.TMS.TMS.servise.KCYVerificationService;
import com.TMS.TMS.status.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KCYVerificationServiceImpl implements KCYVerificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KCYVerificationRepository kcyVerificationRepository;

    @Override
    public KCYVerification createKCYVerification(KCYVerification kcyVerification) throws Exception {

        User user = userRepository.findById(kcyVerification.getId()).orElseThrow(() ->
                new Exception("User not found with this id : " + kcyVerification.getId()));

        KCYVerification verification = new KCYVerification();
        verification.setUser(user);
        verification.setDocumentType(kcyVerification.getDocumentType());
        verification.setDocumentUrl(kcyVerification.getDocumentUrl());
        verification.setVerifiedAt(LocalDateTime.now());
        verification.setVerificationStatus(VerificationStatus.PENDING);

        return kcyVerificationRepository.save(verification);
    }

    @Override
    public KCYVerification getKCYVerificationById(Long id) throws Exception {

        return kcyVerificationRepository.findById(id).orElseThrow(() -> new Exception("KCYVerification not found with this id " + id));
    }

    @Override
    public List<KCYVerification> getAllKCYVerification(VerificationStatus status) {

        return kcyVerificationRepository.findByKCYVerificationStatus(status);
    }

    @Override
    public KCYVerification updateKCYVerificationStatus(Long kcyVerificationId, VerificationStatus status) throws Exception {

        KCYVerification kcyVerification = getKCYVerificationById(kcyVerificationId);
        kcyVerification.setVerificationStatus(status);

        return kcyVerificationRepository.save(kcyVerification);
    }
}
