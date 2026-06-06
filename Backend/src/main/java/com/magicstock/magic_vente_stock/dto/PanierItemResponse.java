package com.magicstock.magic_vente_stock.dto;

import java.math.BigDecimal;

public class PanierItemResponse {

    private Integer id;
    private Integer produitId;
    private String libelle;
    private BigDecimal prix;
    private Integer quantite;
    private Integer stockQuantite;
    private Boolean enStock;
    private String imageLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getStockQuantite() {
        return stockQuantite;
    }

    public void setStockQuantite(Integer stockQuantite) {
        this.stockQuantite = stockQuantite;
    }

    public Boolean getEnStock() {
        return enStock;
    }

    public void setEnStock(Boolean enStock) {
        this.enStock = enStock;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
