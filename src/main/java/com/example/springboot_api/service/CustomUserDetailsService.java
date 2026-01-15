package com.example.springboot_api.service;

import com.example.springboot_api.Model.Permission;
import com.example.springboot_api.Model.User;
import com.example.springboot_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        user.getRoles().stream()
                                .flatMap(role -> {
                                    Stream<String> roleAuth =
                                            Stream.of("ROLE_" + role.getName());

                                    Stream<String> permAuth =
                                            role.getPermissions().stream()
                                                    .map(Permission::getName);

                                    return Stream.concat(roleAuth, permAuth);
                                })
                                .toArray(String[]::new)
                )
                .build();
    }

}
