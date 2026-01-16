package by.smarttour.auth.config;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    // Твой секрет: 7x!A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRfUjXn2r
    private static final String SECRET_KEY = "7x!A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRfUjXn2r";
}