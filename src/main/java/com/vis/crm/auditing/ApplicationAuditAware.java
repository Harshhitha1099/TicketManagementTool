package com.vis.crm.auditing;


import com.vis.crm.userdetails.CustomUserDetails;
import com.vis.crm.userdetails.UserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<UserDetails> {

    @Override
    public Optional<UserDetails> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return Optional.of(customUserDetails.getUserDetails());
    }
}