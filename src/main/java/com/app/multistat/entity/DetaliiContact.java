package com.app.multistat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalii_contact")
public class DetaliiContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String telefon;

    // Relație OneToOne cu Utilizator
    @OneToOne(mappedBy = "detaliiContact")
    private Utilizator utilizator;

    public DetaliiContact() {
    }

    public DetaliiContact(int id, String email, String telefon) {
        this.id = id;
        this.email = email;
        this.telefon = telefon;
    }

    // Getteri și setteri
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
}
