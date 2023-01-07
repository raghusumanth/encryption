package com.encryption.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputData {

    private String data;
    private String key;

}
