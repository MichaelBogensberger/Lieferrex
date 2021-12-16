package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mandant")
public class Mandant {

    @Id
    @Column(name = "mandant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firmenname;
    private String land;
    private String ort;

    @Size(min=4,max = 6)
    private Integer plz;

    private String strasse;
    private String hausnummer;
    private Integer telefon;

    //@Email
    private String email;
    private Double mindestbestellwert;
    private Double lieferkosten;

    @ManyToMany
    @JoinTable(
            name = "mandant_kategorie",
            joinColumns = @JoinColumn(name = "mandant_id"),
            inverseJoinColumns = @JoinColumn(name = "kategorie_id"))
    private Set<Kategorie> kategorie;


    @ManyToMany
    @JoinTable(
            name = "mandant_bestellart",
            joinColumns = @JoinColumn(name = "mandant_id"),
            inverseJoinColumns = @JoinColumn(name = "bestellart_id"))
    private Set<Bestellart> bestellart;


    @OneToMany(mappedBy="mandant")
    private Set<Gericht> gerichte;

    @OneToMany(mappedBy="mandant")
    private Set<Bestellung> bestellungen;

    @OneToMany(mappedBy="mandant")
    private Set<Angestellter> angestellte;


}
