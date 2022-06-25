package com.htlimst.lieferrex.model;

import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
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
@Table(name = "bestellstatus")
public class Bestellstatus {

    @Id
    @Column(name = "bestellstatus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BestellstatusEnum bestellstatus;

    @OneToMany(mappedBy = "bestellstatus")
    private Set<Bestellung> bestellung;


}
