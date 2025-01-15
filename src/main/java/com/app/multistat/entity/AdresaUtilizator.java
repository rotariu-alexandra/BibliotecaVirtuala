package com.app.multistat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "adresa_utilizator")
public class AdresaUtilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String strada;
    private String numar;
    private String oras;
    private String judet;
    private String tara;
    private String codPostal;

    // Relație OneToOne cu Utilizator
    @OneToOne(mappedBy = "adresaUtilizator")
    private Utilizator utilizator;

    public AdresaUtilizator() {
    }

    public AdresaUtilizator(int id, String strada, String numar, String oras, String judet, String tara, String codPostal) {
        this.id = id;
        this.strada = strada;
        this.numar = numar;
        this.oras = oras;
        this.judet = judet;
        this.tara = tara;
        this.codPostal = codPostal;
    }

    // Getteri și setteri
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStrada() { return strada; }
    public void setStrada(String strada) { this.strada = strada; }

    public String getNumar() { return numar; }
    public void setNumar(String numar) { this.numar = numar; }

    public String getOras() { return oras; }
    public void setOras(String oras) { this.oras = oras; }

    public String getJudet() { return judet; }
    public void setJudet(String judet) { this.judet = judet; }

    public String getTara() { return tara; }
    public void setTara(String tara) { this.tara = tara; }

    public String getCodPostal() { return codPostal; }
    public void setCodPostal(String codPostal) { this.codPostal = codPostal; }

    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
}
