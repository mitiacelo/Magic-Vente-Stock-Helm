package com.magicstock.magic_vente_stock.controller;

import com.magicstock.magic_vente_stock.dto.ProduitRequest;
import com.magicstock.magic_vente_stock.model.Produit;
import com.magicstock.magic_vente_stock.service.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public ResponseEntity<List<Produit>> findAll() {
        return ResponseEntity.ok(produitService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Produit> produit = produitService.findById(id);
        if (produit.isPresent()) {
            return ResponseEntity.ok(produit.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produit introuvable : id=" + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProduitRequest request) {
        try {
            Produit created = produitService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProduitRequest request) {
        try {
            Produit updated = produitService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg != null && msg.startsWith("Produit introuvable")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (produitService.delete(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produit introuvable : id=" + id);
        }
    }
}
