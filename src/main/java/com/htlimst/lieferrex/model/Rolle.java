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
@Table(name = "rolle")
public class Rolle {

    @Id
    @Column(name = "rolle_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String rolle;
}
