package com.magicstock.magic_vente_stock.controller;

import com.magicstock.magic_vente_stock.dto.PanierItemResponse;
import com.magicstock.magic_vente_stock.dto.PanierRequest;
import com.magicstock.magic_vente_stock.service.PanierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/panier")
public class PanierController {

    private final PanierService panierService;

    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @GetMapping
    public ResponseEntity<List<PanierItemResponse>> getCart(Principal principal) {
        return ResponseEntity.ok(panierService.getCartItems(principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> addToCart(Principal principal, @RequestBody PanierRequest request) {
        try {
            PanierItemResponse response = panierService.addToCart(principal.getName(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{produitId}")
    public ResponseEntity<?> updateCartItem(Principal principal,
            @PathVariable Integer produitId,
            @RequestBody PanierRequest request) {
        try {
            PanierItemResponse response = panierService.updateCartItem(principal.getName(), produitId,
                    request.getQuantite());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{produitId}")
    public ResponseEntity<?> removeCartItem(Principal principal, @PathVariable Integer produitId) {
        if (panierService.removeCartItem(principal.getName(), produitId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Article non trouvé dans le panier : produitId=" + produitId);
    }
}
