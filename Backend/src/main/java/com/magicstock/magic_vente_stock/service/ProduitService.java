package com.magicstock.magic_vente_stock.service;

import com.magicstock.magic_vente_stock.dto.ProduitRequest;
import com.magicstock.magic_vente_stock.model.Produit;
import com.magicstock.magic_vente_stock.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    public Optional<Produit> findById(Integer id) {
        return produitRepository.findById(id);
    }

    /**
     * Renvoie le "produit du jour" : selection deterministe basee sur le jour
     * de l'annee, de sorte que le meme produit soit affiche toute la journee.
     */
    public Optional<Produit> produitDuJour() {
        List<Produit> produits = produitRepository.findAll();
        if (produits.isEmpty()) {
            return Optional.empty();
        }
        int index = (LocalDate.now().getDayOfYear() - 1) % produits.size();
        return Optional.of(produits.get(index));
    }

    public Produit create(ProduitRequest request) {
        if (request.getLibelle() == null || request.getLibelle().isBlank()) {
            throw new IllegalArgumentException("Le libellé est obligatoire.");
        }
        if (request.getPrix() == null) {
            throw new IllegalArgumentException("Le prix est obligatoire.");
        }
        if (request.getPrix().signum() < 0) {
            throw new IllegalArgumentException("Le prix ne peut pas être négatif.");
        }
        if (request.getQuantite() != null && request.getQuantite() < 0) {
            throw new IllegalArgumentException("La quantité ne peut pas être négative.");
        }

        Produit produit = new Produit();
        produit.setLibelle(request.getLibelle());
        produit.setPrix(request.getPrix());
        produit.setQuantite(request.getQuantite());
        produit.setEnStock(request.getEnStock());
        produit.setImageLink(request.getImageLink());
        return produitRepository.save(produit);
    }

    public Produit update(Integer id, ProduitRequest request) {
        if (request.getLibelle() == null || request.getLibelle().isBlank()) {
            throw new IllegalArgumentException("Le libellé est obligatoire.");
        }
        if (request.getPrix() == null) {
            throw new IllegalArgumentException("Le prix est obligatoire.");
        }
        if (request.getPrix().signum() < 0) {
            throw new IllegalArgumentException("Le prix ne peut pas être négatif.");
        }
        if (request.getQuantite() != null && request.getQuantite() < 0) {
            throw new IllegalArgumentException("La quantité ne peut pas être négative.");
        }

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable : id=" + id));

        produit.setLibelle(request.getLibelle());
        produit.setPrix(request.getPrix());
        produit.setQuantite(request.getQuantite());
        produit.setEnStock(request.getEnStock());
        produit.setImageLink(request.getImageLink());
        return produitRepository.save(produit);
    }

    public boolean delete(Integer id) {
        if (!produitRepository.existsById(id)) {
            return false;
        }
        produitRepository.deleteById(id);
        return true;
    }
}
