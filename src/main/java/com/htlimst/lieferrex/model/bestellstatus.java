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
@Table(name = "bestellstatus")
public class bestellstatus {

    @Id
    @Column(name = "bestellstatus_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String bestellstatus;


}
