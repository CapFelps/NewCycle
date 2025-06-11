package br.com.fiap.newcycle.auth;

import br.com.fiap.newcycle.config.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dto) {
        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        String jwt = tokenService.generateToken(auth);
        return ResponseEntity.ok(new TokenDTO(jwt));
    }
}
