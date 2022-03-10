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
@Table(name = "fragmenttext")
public class FragmentText {


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
