package com.htlimst.lieferrex.model;


import com.htlimst.lieferrex.model.enums.KategorieEnum;
import com.htlimst.lieferrex.model.enums.WochentagEnum;
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


    @Enumerated(EnumType.STRING)
    private KategorieEnum name;


    @OneToMany(mappedBy = "kategorie")
    private Set<Mandant> mandanten;


    public Kategorie(Long id, KategorieEnum name) {
        this.id = id;
        this.name = name;
    }
}
