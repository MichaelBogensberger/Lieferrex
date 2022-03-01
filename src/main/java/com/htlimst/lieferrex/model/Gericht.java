package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "gericht")
@Getter
public class Gericht {

    @Id
    @Column(name = "gericht_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;
    private String beschreibung;
    private Double preis;
    private Double preisangebot;
    private int anzahl_gekauft;
    // 0=deaktiviert, 1=aktiviert, 2=in aktion
    private int status;

    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false, updatable=false)
    private Mandant mandant;


    @OneToMany(mappedBy="gericht")
    private Set<GerichtBestellung> gerichteBestellungen;

    public Gericht(Long id, String name, String beschreibung, Double preis, Double preisangebot, int anzahl_gekauft, int status, Mandant mandant) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.preisangebot = preisangebot;
        this.anzahl_gekauft = anzahl_gekauft;
        this.status = status;
        this.mandant = mandant;
    }
}

