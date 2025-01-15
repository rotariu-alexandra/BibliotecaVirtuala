package com.app.multistat.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Imprumut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator; // legătură către utilizatorul care împrumută

    @ManyToOne
    @JoinColumn(name = "carte_id", nullable = false)
    private Carte carte; // legătură către cartea împrumutată

    @Temporal(TemporalType.DATE)
    private Date dataImprumut; // data la care s-a împrumutat cartea

    @Temporal(TemporalType.DATE)
    private Date dataReturnare; // data limită pentru returnare

    private boolean returnat; // statusul de returnare

    // Constructor implicit
    public Imprumut() {}

    // Constructor complet
    public Imprumut(Utilizator utilizator, Carte carte, Date dataImprumut, Date dataReturnare) {
        this.utilizator = utilizator;
        this.carte = carte;
        this.dataImprumut = dataImprumut;
        this.dataReturnare = dataReturnare;
        this.returnat = false; // implicit, cartea nu este returnată
    }

    // Getteri și Setteri
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }

    public Carte getCarte() { return carte; }
    public void setCarte(Carte carte) { this.carte = carte; }

    public Date getDataImprumut() { return dataImprumut; }
    public void setDataImprumut(Date dataImprumut) { this.dataImprumut = dataImprumut; }

    public Date getDataReturnare() { return dataReturnare; }
    public void setDataReturnare(Date dataReturnare) { this.dataReturnare = dataReturnare; }

    public boolean isReturnat() { return returnat; }
    public void setReturnat(boolean returnat) { this.returnat = returnat; }

    // Metodă pentru verificarea depășirii termenului de returnare
    public boolean esteDepasitTermenul() {
        Date dataCurenta = new Date();
        return !returnat && dataCurenta.after(dataReturnare);
    }
}
