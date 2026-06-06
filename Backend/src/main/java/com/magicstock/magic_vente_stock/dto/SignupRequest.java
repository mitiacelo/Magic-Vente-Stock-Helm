package com.magicstock.magic_vente_stock.dto;

public class SignupRequest {
    private String prenom;
    private String nom;
    private String pseudo;
    private String motDePasse;

    // Getters and Setters
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}
