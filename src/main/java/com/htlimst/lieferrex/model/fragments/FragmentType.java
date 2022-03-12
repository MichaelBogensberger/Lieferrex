package com.htlimst.lieferrex.model.fragments;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.htlimst.lieferrex.model.Fragment;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fragmenttype")
public class FragmentType {


    @Id
    @Column(name = "fragmenttype_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String type;

    @OneToMany(mappedBy = "fragmenttype")
    private Set<Fragment> fragmente;

}
