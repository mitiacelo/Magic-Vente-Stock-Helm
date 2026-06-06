package com.magicstock.magic_vente_stock.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String libelle;

    private BigDecimal prix;

    private Integer quantite;

    @Column(name = "en_stock")
    private Boolean enStock;

    @Column(name = "image_link")
    private String imageLink;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public Boolean getEnStock() { return enStock; }
    public void setEnStock(Boolean enStock) { this.enStock = enStock; }

    public String getImageLink() { return imageLink; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }
}
