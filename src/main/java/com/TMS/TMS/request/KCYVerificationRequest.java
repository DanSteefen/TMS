package com.TMS.TMS.request;

import lombok.Data;

@Data
public class KCYVerificationRequest {

    private Long userId;
    private String documentType;
    private String documentUrl;
}
