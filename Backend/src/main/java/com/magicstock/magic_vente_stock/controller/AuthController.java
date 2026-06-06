package com.magicstock.magic_vente_stock.controller;


import com.magicstock.magic_vente_stock.dto.LoginRequest;
import com.magicstock.magic_vente_stock.dto.SignupRequest;
import com.magicstock.magic_vente_stock.model.Client;
import com.magicstock.magic_vente_stock.service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ClientService clientService;

    public AuthController(ClientService clientService) {
        this.clientService = clientService;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        Optional<Client> loggedInClient = clientService.loginClient(request);

        if (loggedInClient.isPresent()) {
            Client client = loggedInClient.get();
            session.setAttribute("user", client.getPseudo());
            client.setMotDePasse(null);
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants de connexion incorrects.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Déconnexion réussie.");
    }
}
