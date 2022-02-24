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
@Table(name = "layout")
public class Layout {


    @Id
    @Column(name = "layout_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "layout")
    private Set<Position> positionen;

    @OneToMany(mappedBy = "layout")
    private Set<Mandant> mandanten;


}
