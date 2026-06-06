package com.magicstock.magic_vente_stock.repository;

import com.magicstock.magic_vente_stock.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
}
