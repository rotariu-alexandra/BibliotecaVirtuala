package com.app.multistat.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recenzie_carte")
public class RecenzieCarte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relație ManyToOne cu Carte
    @ManyToOne
    @JoinColumn(name = "carte_id", nullable = false)
    private Carte carte;

    // Relație ManyToOne cu Utilizator
    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    private int rating;        // rating de la 1 la 5

    @Column(length = 500)
    private String comentariu; // comentariul recenziei

    // Constructor implicit
    public RecenzieCarte() {
    }

    // Constructor cu parametri
    public RecenzieCarte(Carte carte, Utilizator utilizator, int rating, String comentariu) {
        this.carte = carte;
        this.utilizator = utilizator;
        this.setRating(rating);
        this.comentariu = comentariu;
    }

    // Getteri și Setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating-ul trebuie să fie între 1 și 5.");
        }
    }

    public String getComentariu() {
        return comentariu;
    }

    public void setComentariu(String comentariu) {
        this.comentariu = comentariu;
    }
}
