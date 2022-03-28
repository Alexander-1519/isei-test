package com.example.iseitest.security.jwt;

import com.example.iseitest.entity.User;
import com.example.iseitest.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.days}")
    private String days;

    private final UserRepository userRepository;

    @Autowired
    public JwtProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(String login) {
        User user = userRepository.findByEmail(login)
                .orElseThrow(() -> new UsernameNotFoundException("user is not exist with username = " + login));
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getUserRole());
        claims.put("username", login);
        Date date = Date.from(LocalDate.now().plusDays(Integer.parseInt(days))
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            LOG.warn("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return (String) claims.get("username");
    }
}
