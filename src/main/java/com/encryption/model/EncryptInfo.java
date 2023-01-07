package com.encryption.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EncryptInfo {

    private String processK;
    private String processD;

}
