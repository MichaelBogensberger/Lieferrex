package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bestellung")
public class Bestellung {

    @Id
    @Column(name = "bestellung_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dauer;
    private Timestamp bestelldatum;
    private Double gesamtpreis;
    private Double trinkgeld;


    @ManyToMany
    @JoinTable(
            name = "bestellung_bestellstatus",
            joinColumns = @JoinColumn(name = "bestellung_id"),
            inverseJoinColumns = @JoinColumn(name = "bestellstatus_id"))
    private Set<Bestellstatus> bestellstatus;



    @ManyToOne
    @JoinColumn(name="bestellart_id", nullable=false, insertable=false, updatable=false)
    private Bestellart bestellart;


    @ManyToOne
    @JoinColumn(name="kunde_id", nullable=false, insertable=false, updatable=false)
    private Kunde kunde;

    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false, insertable=false, updatable=false)
    private Mandant mandant;


    @OneToMany(mappedBy="bestellung")
    private Set<GerichtBestellung> gerichteBestellungen;


}

