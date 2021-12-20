package com.htlimst.lieferrex.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fragmenttype")
public class Fragmenttype {

    @Id
    @Column(name = "fragmenttype_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fragmenttype;

    @OneToMany(mappedBy = "fragmenttype")
    private Set<Fragment> fragmente;

}
