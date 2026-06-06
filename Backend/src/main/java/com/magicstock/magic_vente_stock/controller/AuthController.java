package com.magicstock.magic_vente_stock.controller;


import com.magicstock.magic_vente_stock.config.JwtService;
import com.magicstock.magic_vente_stock.dto.LoginRequest;
import com.magicstock.magic_vente_stock.dto.SignupRequest;
import com.magicstock.magic_vente_stock.model.Client;
import com.magicstock.magic_vente_stock.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ClientService clientService;
    private final JwtService jwtService;

    public AuthController(ClientService clientService, JwtService jwtService) {
        this.clientService = clientService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            Client registeredClient = clientService.registerClient(request);
            registeredClient.setMotDePasse(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Client> loggedInClient = clientService.loginClient(request);

        if (loggedInClient.isPresent()) {
            Client client = loggedInClient.get();
            String token = jwtService.generateToken(client.getPseudo());
            client.setMotDePasse(null);

            Map<String, Object> body = new HashMap<>();
            body.put("client", client);
            body.put("token", token);
            return ResponseEntity.ok(body);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants de connexion incorrects.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Déconnexion réussie.");
    }
}
