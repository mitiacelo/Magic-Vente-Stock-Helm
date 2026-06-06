package com.magicstock.magic_vente_stock.service;


import com.magicstock.magic_vente_stock.dto.LoginRequest;
import com.magicstock.magic_vente_stock.dto.SignupRequest;
import com.magicstock.magic_vente_stock.model.Client;
import com.magicstock.magic_vente_stock.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client registerClient(SignupRequest request) {
        if (clientRepository.existsByPseudo(request.getPseudo())) {
            throw new IllegalArgumentException("Ce pseudo est déjà utilisé.");
        }

        Client client = new Client();
        client.setPrenom(request.getPrenom());
        client.setNom(request.getNom());
        client.setPseudo(request.getPseudo());
        client.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));

        return clientRepository.save(client);
    }

    public Optional<Client> loginClient(LoginRequest request) {
        Optional<Client> clientOpt = clientRepository.findByPseudo(request.getPseudo());

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            if (passwordEncoder.matches(request.getMotDePasse(), client.getMotDePasse())) {
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }
}
