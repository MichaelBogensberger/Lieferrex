package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "kunde")
public class Kunde {

    @Id
    @Column(name = "kunde_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String vorname;
    private String nachname;
    private String benutzername;
    private String email;
    private String passwort;
    private String ort;

    @Size(min=4,max = 6)
    private String plz;
    private String strasse;
    private String hausnummer;
    private String telefonnummer;
    private String land;
    private String newsletter;
}
