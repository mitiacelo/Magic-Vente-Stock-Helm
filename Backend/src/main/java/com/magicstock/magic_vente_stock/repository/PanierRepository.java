package com.magicstock.magic_vente_stock.repository;

import com.magicstock.magic_vente_stock.model.Client;
import com.magicstock.magic_vente_stock.model.Panier;
import com.magicstock.magic_vente_stock.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PanierRepository extends JpaRepository<Panier, Integer> {
    List<Panier> findByClient(Client client);

    Optional<Panier> findByClientAndProduit(Client client, Produit produit);

    void deleteByClientAndProduit(Client client, Produit produit);
}
