package com.htlimst.lieferrex.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bestellart")
public class Bestellart {

    @Id
    @Column(name = "bestellart_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String bestellart;





    @OneToMany(mappedBy="bestellart")
    private Set<Bestellung> bestellungen;




}
