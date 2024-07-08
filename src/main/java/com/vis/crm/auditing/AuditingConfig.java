package com.vis.crm.auditing;

import com.vis.crm.userdetails.UserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;


@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<UserDetails> auditorProvider() {
        return new ApplicationAuditAware();
    }
}
