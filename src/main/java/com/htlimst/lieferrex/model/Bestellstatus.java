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
@Table(name = "bestellstatus")
public class Bestellstatus {

    @Id
    @Column(name = "bestellstatus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bestellstatus;



}
