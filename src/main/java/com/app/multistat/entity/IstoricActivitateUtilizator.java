package com.app.multistat.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "istoric_activitate_utilizator")
public class IstoricActivitateUtilizator {
//ENUM
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String activitate;
    private Date dataActivitate;

    // Relație ManyToOne cu Utilizator
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilizator_id")
    private Utilizator utilizator;

    public IstoricActivitateUtilizator() {
    }

    public IstoricActivitateUtilizator(int id, Utilizator utilizator, String activitate, Date dataActivitate) {
        this.id = id;
        this.utilizator = utilizator;
        this.activitate = activitate;
        this.dataActivitate = dataActivitate;
    }

    // Getteri și setteri
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }

    public String getActivitate() { return activitate; }
    public void setActivitate(String activitate) { this.activitate = activitate; }

    public Date getDataActivitate() { return dataActivitate; }
    public void setDataActivitate(Date dataActivitate) { this.dataActivitate = dataActivitate; }
}
