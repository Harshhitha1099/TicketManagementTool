package com.vis.crm.userdetails;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Loading user by username: " + username);
        Optional<UserDetails> userDetailsOptional = userRepository.findByUsername(username);

        UserDetails user = userDetailsOptional.orElseThrow(() -> {
            LOGGER.warning("User not found with username: " + username);
            return new UsernameNotFoundException("User not found with username: " + username);
        });

        return new CustomUserDetails(user);
    }
}