package com.TMS.TMS.controller;

import com.TMS.TMS.modules.KCYVerification;
import com.TMS.TMS.servise.KCYVerificationService;
import com.TMS.TMS.status.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kcyVerification")
@RequiredArgsConstructor
public class KCYVerificationController {

    @Autowired
    private KCYVerificationService kcyVerificationService;

    @PostMapping("/createKCYVerification")
    public ResponseEntity<KCYVerification> createKCYVerification(@RequestBody KCYVerification kcyVerification) throws Exception{

        KCYVerification verification = kcyVerificationService.createKCYVerification(kcyVerification);
        return new ResponseEntity<>(verification, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KCYVerification> getKCYVerificationById(@PathVariable Long id) throws Exception {

        KCYVerification kcyVerification = kcyVerificationService.getKCYVerificationById(id);
        return new ResponseEntity<>(kcyVerification, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<KCYVerification>> getAllKCYVerification(@RequestParam(required = false)VerificationStatus status){

        List<KCYVerification> kcyVerifications = kcyVerificationService.getAllKCYVerification(status);
        return new ResponseEntity<>(kcyVerifications, HttpStatus.OK);
    }

}
