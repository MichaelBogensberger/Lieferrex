package com.htlimst.lieferrex.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bestellung")
@Builder
public class Bestellung {

    @Id
    @Column(name = "bestellung_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dauer;
    private Timestamp bestelldatum;
    private double gesamtpreis;
    private double trinkgeld;


    @ManyToOne
    @JoinColumn(name="bestellart_id", nullable=false, updatable=false)
    private Bestellart bestellart;

    @ManyToOne
    @JoinColumn(name="kunde_id", nullable=false, updatable=false)
    private Kunde kunde;

    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false, updatable=false)
    private Mandant mandant;


    @OneToMany(mappedBy="bestellung")
    private Set<GerichtBestellung> gerichteBestellungen;

    @ManyToMany
    @JoinTable(
            name = "bestellung_bestellstatus",
            joinColumns = @JoinColumn(name = "bestellung_id"),
            inverseJoinColumns = @JoinColumn(name = "bestellstatus_id"))
    private Set<Bestellstatus> bestellstatus;

    public Bestellung(Long id, int dauer, Timestamp bestelldatum, double gesamtpreis, double trinkgeld, Bestellart bestellart, Kunde kunde, Mandant mandant, Set<Bestellstatus> bestellstatus) {
        this.id = id;
        this.dauer = dauer;
        this.bestelldatum = bestelldatum;
        this.gesamtpreis = gesamtpreis;
        this.trinkgeld = trinkgeld;
        this.bestellart = bestellart;
        this.kunde = kunde;
        this.mandant = mandant;
        this.bestellstatus = bestellstatus;
    }
}

