package com.htlimst.lieferrex.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fragmenttext")
public class Fragmenttext {


    @Id
    @Column(name = "fragmenttext_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String text;

    @NotEmpty
    private String farbe;

    @OneToOne
    @JoinColumn(name="fragment_id", nullable=false, insertable=false, updatable=false)
    private Fragment fragment;

}