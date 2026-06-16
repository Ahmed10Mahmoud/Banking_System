package example.wep.app.service;

import example.wep.app.dto.*;
import example.wep.app.entity.Role;
import example.wep.app.entity.User;
import example.wep.app.exception.UserAlreadyExistsException;
import example.wep.app.repository.RoleRepository;
import example.wep.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.username())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("ROLE_USER not found"));

        User user = User.builder()
                .firstname(request.firstName())
                .lastname(request.lastName())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .gender(request.gender())
                .roles(List.of(role))
                .build();
        User saved =  userRepository.save(user);
        return UserResponse.builder()
                .uid(saved.getUid())
                .firstName(saved.getFirstname())
                .lastName(saved.getLastname())
                .username(saved.getUsername())
                .build();
    }
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository
                .findByUsernameIgnoreCase(request.username())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
