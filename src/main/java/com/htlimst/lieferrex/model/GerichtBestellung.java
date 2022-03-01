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
@Table(name = "gericht_bestellung")
public class GerichtBestellung {

    @Id
    @Column(name = "gericht_betsellung_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String anmerkung;

    @ManyToOne
    @JoinColumn(name="gericht_id", nullable=false, updatable=false)
    private Gericht gericht;


    @ManyToOne
    @JoinColumn(name="bestellung_id", nullable=false, updatable=false)
    private Bestellung bestellung;


}
