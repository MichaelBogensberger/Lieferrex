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
@Table(name = "angestellter")
public class Angestellter {

    @Id
    @Column(name = "angestellter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false)
    private Mandant mandant;

    private String vorname;
    private String nachname;
    private String benutzername;
    private String passwort;

    @ManyToMany
    @JoinTable(
            name = "angestellter_rolle",
            joinColumns = @JoinColumn(name = "angestellter_id"),
            inverseJoinColumns = @JoinColumn(name = "rolle_id"))
    private Set<Rolle> rolle;

}

