package com.backend.demo.registrationservice.config.security;

import com.backend.demo.registrationservice.model.user.UserInfo;
import com.backend.demo.registrationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.getUserInfo(userName);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User '" + userName + "' not found");
        }

        return User.withUsername(userInfo.getUserName())
                .password(userInfo.getPassword())
                .authorities("DEFAULT")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
