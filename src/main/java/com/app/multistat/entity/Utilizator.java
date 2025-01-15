package com.app.multistat.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "utilizator")
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nume;
    private String prenume;
    private String rol; // "admin" sau "utilizator"

    private String email;  // Adăugăm câmpul email

    // Relație OneToOne cu DetaliiContact
    @OneToOne
    @JoinColumn(name = "detalii_contact_id")
    private DetaliiContact detaliiContact;

    // Relație OneToOne cu AdresaUtilizator
    @OneToOne
    @JoinColumn(name = "adresa_utilizator_id")
    private AdresaUtilizator adresaUtilizator;

    // Relație OneToMany cu IstoricActivitateUtilizator
    @OneToMany(mappedBy = "utilizator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IstoricActivitateUtilizator> istoricActivitati;

    // Relație OneToMany cu Imprumut
    @OneToMany(mappedBy = "utilizator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Imprumut> imprumuturi;

    // Relație OneToMany cu RecenzieCarte
    @OneToMany(mappedBy = "utilizator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecenzieCarte> recenzii;

    public Utilizator() {
    }

    public Utilizator(int id, String nume, String prenume, String email, DetaliiContact detaliiContact, AdresaUtilizator adresaUtilizator, String rol) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;  // Setăm email în constructor
        this.detaliiContact = detaliiContact;
        this.adresaUtilizator = adresaUtilizator;
        this.rol = rol;
    }

    // Getteri și Setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;  // Setăm email
    }

    public DetaliiContact getDetaliiContact() {
        return detaliiContact;
    }

    public void setDetaliiContact(DetaliiContact detaliiContact) {
        this.detaliiContact = detaliiContact;
    }

    public AdresaUtilizator getAdresaUtilizator() {
        return adresaUtilizator;
    }

    public void setAdresaUtilizator(AdresaUtilizator adresaUtilizator) {
        this.adresaUtilizator = adresaUtilizator;
    }

    public List<IstoricActivitateUtilizator> getIstoricActivitati() {
        return istoricActivitati;
    }

    public void setIstoricActivitati(List<IstoricActivitateUtilizator> istoricActivitati) {
        this.istoricActivitati = istoricActivitati;
    }

    public List<Imprumut> getImprumuturi() {
        return imprumuturi;
    }

    public void setImprumuturi(List<Imprumut> imprumuturi) {
        this.imprumuturi = imprumuturi;
    }

    public List<RecenzieCarte> getRecenzii() {
        return recenzii;
    }

    public void setRecenzii(List<RecenzieCarte> recenzii) {
        this.recenzii = recenzii;
    }
}