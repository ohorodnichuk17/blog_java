package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.configuration.security.JwtService;
import org.example.constants.Roles;
import org.example.dto.account.AuthResponseDto;
import org.example.dto.account.LoginDto;
import org.example.dto.account.RegisterDto;
import org.example.entities.RoleEntity;
import org.example.entities.UserEntity;
import org.example.entities.UserRoleEntity;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.UserRoleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;

    public AuthResponseDto login(LoginDto request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var isValid = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!isValid) {
            throw new UsernameNotFoundException("User not found");
        }
        var jwtToekn = jwtService.generateAccessToken(user);
        return AuthResponseDto.builder()
                .token(jwtToekn)
                .build();
    }

    public UserEntity register(RegisterDto dto) throws Exception {
        if(!dto.getPassword().equals(dto.getRepeatPassword()))throw new Exception("Не ідентичні паролі");
        UserEntity newUser = UserEntity.builder().
                firstName(dto.getName()).
                lastName(dto.getSurname()).
                email(dto.getEmail()).
                phone(dto.getPhoneNumber()).
                password(passwordEncoder.encode(dto.getPassword())).
                build();
        userRepository.save(newUser);

        RoleEntity role = roleRepository.findByName(Roles.User);

        var ur = UserRoleEntity
                .builder()
                .role(role)
                .user(newUser)
                .build();

        userRoleRepository.save(ur);
        return newUser;
    }
}