package com.htlimst.lieferrex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotEmpty
    private String name;
    private String beschreibung;
    private Double preis;
    private Double preisangebot;
    private int anzahl_gekauft;
    // 0=deaktiviert, 1=aktiviert, 2=in aktion
    private int status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false, insertable=false, updatable=false)
    private Mandant mandant;

    @JsonIgnore
    @OneToMany(mappedBy="gericht")
    private Set<GerichtBestellung> gerichteBestellungen;





}

