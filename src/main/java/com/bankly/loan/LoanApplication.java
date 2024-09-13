
package com.bankly.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.bankly.loan.dto.EnvPropertiesDto;

@SpringBootApplication
@EnableConfigurationProperties(value={EnvPropertiesDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}
