package com.example.iseitest.service;

import com.example.iseitest.entity.User;
import com.example.iseitest.entity.UserRole;
import com.example.iseitest.entity.UserRoleName;
import com.example.iseitest.exception.NoSuchUserException;
import com.example.iseitest.exception.PasswordNotMatchException;
import com.example.iseitest.exception.UserAlreadyExistsException;
import com.example.iseitest.repository.UserRepository;
import com.example.iseitest.repository.UserRoleRepository;
import com.example.iseitest.security.SecurityUserDetailService;
import com.example.iseitest.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityUserDetailService securityService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository,
                       SecurityUserDetailService securityService,
                       PasswordEncoder passwordEncoder,
                       UserRoleRepository userRoleRepository,
                       JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.jwtProvider = jwtProvider;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        UserRole userRole = userRoleRepository.findByName(UserRoleName.USER)
                .orElseThrow(() -> new UsernameNotFoundException(UserRoleName.USER.name()));
        user.setUserRole(userRole);

        return userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchUserException(email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordNotMatchException(password);
        }

        return jwtProvider.generateToken(email);
    }
}
