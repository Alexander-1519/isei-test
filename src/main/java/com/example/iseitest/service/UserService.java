package com.example.iseitest.service;

import com.example.iseitest.entity.Company;
import com.example.iseitest.entity.User;
import com.example.iseitest.entity.UserRole;
import com.example.iseitest.entity.UserRoleName;
import com.example.iseitest.exception.*;
import com.example.iseitest.repository.CompanyRepository;
import com.example.iseitest.repository.UserRepository;
import com.example.iseitest.repository.UserRoleRepository;
import com.example.iseitest.security.SecurityUserDetailService;
import com.example.iseitest.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityUserDetailService securityService;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository,
                       SecurityUserDetailService securityService,
                       CompanyRepository companyRepository,
                       PasswordEncoder passwordEncoder,
                       UserRoleRepository userRoleRepository,
                       JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.companyRepository = companyRepository;
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

    public User updateUser(User user, String email, Long userId) {
        User userFromDb = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException(email));
        if (!userFromDb.getId().equals(userId)) {
            throw ExceptionBuilder.builder(Code.UNEXPECTED)
                    .withMessage("You have no permission to this user")
                    .build(IseiException.class);
        }

        if (user.getCompany() != null) {
            String companyName = user.getCompany().getName();
            Company company = companyRepository.findByName(companyName)
                    .orElseThrow(() -> new NoSuchCompanyException(companyName));
            userFromDb.setCompany(company);
        }

        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());

        return userRepository.save(userFromDb);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchUserException(id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getAuthUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException(email));
    }
}
