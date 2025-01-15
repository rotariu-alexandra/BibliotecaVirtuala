package com.app.multistat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalii_carte")
public class DetaliiCarte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String statusCarte; // "disponibila" sau "imprumutata"
    private String descriereCarte;

    // Relație inversă OneToOne cu Carte
    @OneToOne(mappedBy = "detaliiCarte")
    private Carte carte;

    // Constructor
    public DetaliiCarte(int id, String statusCarte, String descriereCarte) {
        this.id = id;
        this.statusCarte = statusCarte;
        this.descriereCarte = descriereCarte;
    }

    // Constructor implicit
    public DetaliiCarte() {
    }

    // Getteri și setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusCarte() {
        return statusCarte;
    }

    public void setStatusCarte(String statusCarte) {
        this.statusCarte = statusCarte;
    }

    public String getDescriereCarte() {
        return descriereCarte;
    }

    public void setDescriereCarte(String descriereCarte) {
        this.descriereCarte = descriereCarte;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }
}
