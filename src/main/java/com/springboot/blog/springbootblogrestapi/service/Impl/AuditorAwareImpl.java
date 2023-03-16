package com.springboot.blog.springbootblogrestapi.service.Impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    @Transactional
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

  
        Optional<String> username = Optional.ofNullable(authentication.getName().toString()).filter(s -> !s.isEmpty());
        return username ;
    }
}
