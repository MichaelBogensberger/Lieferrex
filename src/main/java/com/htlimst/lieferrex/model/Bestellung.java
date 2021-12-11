package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bestellung")
public class Bestellung {

    @Id
    @Column(name = "bestellung_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "mandat_id")
    private String mandatId;

    @Column(name = "kunde_id")
    private String kundeId;

    private String bestellart_id;
    private String bestelldatum;
    private String dauer;
    private String bestellzeit;
    private String gesamtpreis;
    private String trinkgeld;
}

