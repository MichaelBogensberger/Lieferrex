package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fragment")
public class Fragment {

    @Id
    @Column(name = "fragment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false)
    private Mandant mandant;

    private String title;
    private String beschreibung;
    private String image;

    @ManyToOne
    @JoinColumn(name="fragmenttype_id", nullable=false)
    private Fragmenttype fragmenttype;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "positionid_id", referencedColumnName = "position_id")
    private Position position;
}

