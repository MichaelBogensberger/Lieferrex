package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "gericht")
@Getter
public class Gericht {

    @Id
    @Column(name = "gericht_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String beschreibung;
    private Double preis;


    @ManyToMany
    @JoinTable(
            name = "gericht_gerichtstatus",
            joinColumns = @JoinColumn(name = "gericht_id"),
            inverseJoinColumns = @JoinColumn(name = "gerichtstatus_id"))
    private Set<Gerichtstatus> gerichtstatus;


}

