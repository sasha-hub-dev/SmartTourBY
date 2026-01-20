package by.smarttour.auth.service;

import by.smarttour.auth.entity.User;
import by.smarttour.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(String email, String password, String role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password)) // хэшик пароля
                .role(by.smarttour.auth.entity.Role.valueOf(role))
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }
}