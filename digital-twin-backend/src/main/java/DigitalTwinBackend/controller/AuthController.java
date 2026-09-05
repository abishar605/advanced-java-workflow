package DigitalTwinBackend.controller;

import DigitalTwinBackend.dto.LoginRequest;
import DigitalTwinBackend.dto.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request,
            jakarta.servlet.http.HttpServletRequest httpRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        httpRequest.getSession(true)
                .setAttribute(
                        "SPRING_SECURITY_CONTEXT",
                        new org.springframework.security.core.context.SecurityContextImpl(authentication)
                );

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        return new LoginResponse(
                "Login successful",
                authentication.getName(),
                role
        );
    }
}