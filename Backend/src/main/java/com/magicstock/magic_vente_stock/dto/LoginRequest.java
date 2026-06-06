package com.magicstock.magic_vente_stock.dto;

public class LoginRequest {
    private String pseudo;
    private String motDePasse;

    // Getters and Setters
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}