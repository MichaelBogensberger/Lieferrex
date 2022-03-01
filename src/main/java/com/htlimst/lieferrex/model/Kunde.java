package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;



@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "kunde")
public class Kunde {

    @Id
    @Column(name = "kunde_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vorname;
    private String nachname;
    private String email;
    private String passwort;
    private String ort;

    //@Size(min=4,max = 6)
    private int plz;
    private String strasse;
    private String hausnummer;
    private int telefonnummer;
    private String land;
    private boolean newsletter;

    public Kunde(Long id, String vorname, String nachname, String email, String passwort, String ort, int plz, String strasse, String hausnummer, int telefonnummer, String land, boolean newsletter) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.telefonnummer = telefonnummer;
        this.land = land;
        this.newsletter = newsletter;
    }
}
