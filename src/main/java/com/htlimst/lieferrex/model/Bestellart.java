package com.htlimst.lieferrex.model;


import com.htlimst.lieferrex.model.enums.BestellartEnum;
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
@Table(name = "bestellart")
public class Bestellart {

    @Id
    @Column(name = "bestellart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BestellartEnum bestellart;

    @OneToMany(mappedBy="bestellart")
    private Set<Bestellung> bestellungen;


    public Bestellart(Long id, BestellartEnum bestellart) {
        this.id = id;
        this.bestellart = bestellart;
    }
}
