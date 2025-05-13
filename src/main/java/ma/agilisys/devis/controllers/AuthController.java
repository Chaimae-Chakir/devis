package ma.agilisys.devis.controllers;

import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.LoginRequest;
import ma.agilisys.devis.utils.KeycloakJwtUtil;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final KeycloakJwtUtil keycloakJwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        AccessTokenResponse tokenResponse = keycloakJwtUtil.getToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return ResponseEntity.ok(tokenResponse);
    }
}
