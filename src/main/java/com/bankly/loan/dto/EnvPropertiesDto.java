package com.bankly.loan.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "loan")
@Getter
@Setter
public class EnvPropertiesDto {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
    private String accountsApiUrl;
}
