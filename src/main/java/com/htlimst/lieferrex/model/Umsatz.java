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
@Table(name = "umsatz")
@Getter
public class Umsatz {

    @Id
    @Column(name = "umsatz_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int monat;
    private int jahr;
    private Double Umsatz;


    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false, insertable=false, updatable=false)
    private Mandant mandant;



}
