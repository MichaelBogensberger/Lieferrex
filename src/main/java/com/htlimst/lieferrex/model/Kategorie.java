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
@Table(name = "kategorie")
public class Kategorie {


    @Id
    @Column(name = "kategorie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // There are following types of categories:
    // --> Fine Dining
    // --> Casual Dining
    // --> Family Style
    // --> Fast Food
    // --> Buffet
    // --> other
    @NotEmpty
    private String name;


    @OneToMany(mappedBy = "kategorie")
    private Set<Mandant> mandanten;



}
