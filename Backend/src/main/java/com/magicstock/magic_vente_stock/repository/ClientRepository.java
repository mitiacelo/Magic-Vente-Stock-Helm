package com.magicstock.magic_vente_stock.repository;

import com.magicstock.magic_vente_stock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByPseudo(String pseudo);
    boolean existsByPseudo(String pseudo);
}