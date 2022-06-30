package com.htlimst.lieferrex.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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
    private Integer bewertung;


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

    @ManyToOne()
    @JoinColumn(name="bestellstatus_id", nullable = false)
    private Bestellstatus bestellstatus;

    public Bestellung(Long id, int dauer, Timestamp bestelldatum, double gesamtpreis, double trinkgeld, Bestellart bestellart, Kunde kunde, Mandant mandant, Bestellstatus bestellstatus, Integer bewertung) {
        this.id = id;
        this.dauer = dauer;
        this.bestelldatum = bestelldatum;
        this.gesamtpreis = gesamtpreis;
        this.trinkgeld = trinkgeld;
        this.bestellart = bestellart;
        this.kunde = kunde;
        this.mandant = mandant;
        this.bestellstatus = bestellstatus;
        this.bewertung = bewertung;
    }
}

