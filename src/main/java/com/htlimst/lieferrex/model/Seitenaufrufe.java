package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "seitenaufrufe")
@Getter
public class Seitenaufrufe {

    @Id
    @Column(name = "seitenaufrufe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int monat;
    private int jahr;
    private int aufrufe;

    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false, updatable=false)
    private Mandant mandant;


}
