package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gericht")
public class Gericht {

    @Id
    @Column(name = "gericht_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mandat_id")
    private String mandatId;

    private String name;
    private String beschreibung;
    private Double preis;


    @ManyToMany
    private Set<Gerichtstatus> gerichtstatus;


}

