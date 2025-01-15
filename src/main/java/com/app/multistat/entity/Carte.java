package com.app.multistat.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "carte")
public class Carte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String domeniu;
    private String titlu;

    // Relație ManyToOne cu CategorieCarte
    @ManyToOne
    @JoinColumn(name = "categorie_id", referencedColumnName = "id")
    private CategorieCarte categorie;

    // Relație OneToOne cu DetaliiCarte
    @OneToOne
    @JoinColumn(name = "detalii_id", referencedColumnName = "id")
    private DetaliiCarte detaliiCarte;

    // Relație OneToMany cu Imprumut
    @OneToMany(mappedBy = "carte", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Imprumut> imprumuturi;

    // Relație OneToMany cu RecenzieCarte
    @OneToMany(mappedBy = "carte", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecenzieCarte> recenzii;

    // Constructor implicit
    public Carte() {
    }

    // Constructor cu parametri
    public Carte(Integer id, String titlu, CategorieCarte categorie, DetaliiCarte detaliiCarte, String domeniu) {
        this.id = id;
        this.titlu = titlu;
        this.categorie = categorie;
        this.detaliiCarte = detaliiCarte;
        this.domeniu=domeniu;
    }

    // Getteri și Setteri
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public CategorieCarte getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieCarte categorie) {
        this.categorie = categorie;
    }

    public DetaliiCarte getDetaliiCarte() {
        return detaliiCarte;
    }

    public void setDetaliiCarte(DetaliiCarte detaliiCarte) {
        this.detaliiCarte = detaliiCarte;
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
