package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mandant")
public class Mandant {

    @Id
    @Column(name = "mandant_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firmenname;
    private String land;
    private String ort;

    @Size(min=4,max = 6)
    private String plz;

    private String strasse;
    private Integer hausnummer;
    private String telefon;

    //@Email
    private String email;
    private String mindestbestellwert;
    private String lieferkosten;


}
