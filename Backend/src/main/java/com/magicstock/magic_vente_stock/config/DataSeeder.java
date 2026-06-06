package com.magicstock.magic_vente_stock.config;

import com.magicstock.magic_vente_stock.dto.SignupRequest;
import com.magicstock.magic_vente_stock.model.Produit;
import com.magicstock.magic_vente_stock.repository.ClientRepository;
import com.magicstock.magic_vente_stock.repository.ProduitRepository;
import com.magicstock.magic_vente_stock.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Insere des donnees de test au demarrage (client + produits) si elles sont absentes.
 * Idempotent : ne fait rien si les donnees existent deja.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;

    public DataSeeder(ClientService clientService,
                      ClientRepository clientRepository,
                      ProduitRepository produitRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
    }

    @Override
    public void run(String... args) {
        seedClient();
        seedProduits();
    }

    private void seedClient() {
        String pseudo = "Aragorn_II";
        if (clientRepository.existsByPseudo(pseudo)) {
            return;
        }
        SignupRequest request = new SignupRequest();
        request.setPrenom("Aragorn");
        request.setNom("Elessar");
        request.setPseudo(pseudo);
        request.setMotDePasse("anduril"); // mot de passe de demo, hashe via BCrypt
        clientService.registerClient(request);
    }

    private void seedProduits() {
        if (produitRepository.count() > 0) {
            return;
        }
        produitRepository.saveAll(List.of(
                produit("Cape de Lorien", new BigDecimal("150.00"), 7, "/images/cape.jpg"),
                produit("Armure de Gondor", new BigDecimal("420.00"), 12, "/images/armure.jpg"),
                produit("Tunique de Minas Tirith", new BigDecimal("85.00"), 34, "/images/tunique.jpg")
        ));
    }

    private Produit produit(String libelle, BigDecimal prix, int quantite, String imageLink) {
        Produit p = new Produit();
        p.setLibelle(libelle);
        p.setPrix(prix);
        p.setQuantite(quantite);
        p.setEnStock(quantite > 0);
        p.setImageLink(imageLink);
        return p;
    }
}
