package com.magicstock.magic_vente_stock.service;

import com.magicstock.magic_vente_stock.dto.PanierItemResponse;
import com.magicstock.magic_vente_stock.dto.PanierRequest;
import com.magicstock.magic_vente_stock.model.Client;
import com.magicstock.magic_vente_stock.model.Panier;
import com.magicstock.magic_vente_stock.model.Produit;
import com.magicstock.magic_vente_stock.repository.ClientRepository;
import com.magicstock.magic_vente_stock.repository.PanierRepository;
import com.magicstock.magic_vente_stock.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanierService {

    private final PanierRepository panierRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;

    public PanierService(PanierRepository panierRepository,
            ClientRepository clientRepository,
            ProduitRepository produitRepository) {
        this.panierRepository = panierRepository;
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
    }

    public List<PanierItemResponse> getCartItems(String pseudo) {
        Client client = findClient(pseudo);
        return panierRepository.findByClient(client)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PanierItemResponse addToCart(String pseudo, PanierRequest request) {
        validateRequest(request);

        Client client = findClient(pseudo);
        Produit produit = findProduit(request.getProduitId());

        if (produit.getQuantite() != null && request.getQuantite() > produit.getQuantite()) {
            throw new IllegalArgumentException("Quantité demandée supérieure au stock disponible.");
        }

        Panier panier = panierRepository.findByClientAndProduit(client, produit)
                .orElseGet(() -> {
                    Panier newPanier = new Panier();
                    newPanier.setClient(client);
                    newPanier.setProduit(produit);
                    newPanier.setQuantite(0);
                    return newPanier;
                });

        panier.setQuantite(panier.getQuantite() + request.getQuantite());
        return mapToResponse(panierRepository.save(panier));
    }

    public PanierItemResponse updateCartItem(String pseudo, Integer produitId, Integer quantite) {
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être un entier positif.");
        }

        Client client = findClient(pseudo);
        Produit produit = findProduit(produitId);
        Panier panier = panierRepository.findByClientAndProduit(client, produit)
                .orElseThrow(() -> new IllegalArgumentException("L'article n'existe pas dans le panier."));

        if (produit.getQuantite() != null && quantite > produit.getQuantite()) {
            throw new IllegalArgumentException("Quantité demandée supérieure au stock disponible.");
        }

        panier.setQuantite(quantite);
        return mapToResponse(panierRepository.save(panier));
    }

    public boolean removeCartItem(String pseudo, Integer produitId) {
        Client client = findClient(pseudo);
        Produit produit = findProduit(produitId);

        if (panierRepository.findByClientAndProduit(client, produit).isEmpty()) {
            return false;
        }

        panierRepository.deleteByClientAndProduit(client, produit);
        return true;
    }

    private Client findClient(String pseudo) {
        return clientRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable : pseudo=" + pseudo));
    }

    private Produit findProduit(Integer produitId) {
        return produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable : id=" + produitId));
    }

    private void validateRequest(PanierRequest request) {
        if (request.getProduitId() == null) {
            throw new IllegalArgumentException("L'identifiant du produit est requis.");
        }
        if (request.getQuantite() == null || request.getQuantite() <= 0) {
            throw new IllegalArgumentException("La quantité doit être un entier positif.");
        }
    }

    private PanierItemResponse mapToResponse(Panier panier) {
        Produit produit = panier.getProduit();
        PanierItemResponse response = new PanierItemResponse();
        response.setId(panier.getId());
        response.setProduitId(produit.getId());
        response.setLibelle(produit.getLibelle());
        response.setPrix(produit.getPrix());
        response.setQuantite(panier.getQuantite());
        response.setStockQuantite(produit.getQuantite());
        response.setEnStock(produit.getEnStock());
        response.setImageLink(produit.getImageLink());
        return response;
    }
}
