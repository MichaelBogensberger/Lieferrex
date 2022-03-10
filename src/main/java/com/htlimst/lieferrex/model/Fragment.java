package com.htlimst.lieferrex.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name="position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name="mandant_id")
    private Mandant mandant;


    @OneToOne(mappedBy = "fragment")
    private Fragmenttext fragmenttext;

    @OneToOne(mappedBy = "fragment")
    private Fragmentmap fragmentmap;
}
