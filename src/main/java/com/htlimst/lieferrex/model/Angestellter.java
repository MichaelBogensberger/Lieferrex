package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Collection;
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
    private String email;
    private String passwort;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "angestellter_rolle",
            joinColumns = @JoinColumn(name = "angestellter_id"),
            inverseJoinColumns = @JoinColumn(name = "rolle_id"))
    private Collection<Rolle> rolle;

    public Angestellter(Long id, Mandant mandant, String vorname, String nachname, String email, String passwort) {
        this.id = id;
        this.mandant = mandant;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
    }
}

