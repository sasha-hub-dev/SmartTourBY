package by.smarttour.auth.dto;

import by.smarttour.auth.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
}