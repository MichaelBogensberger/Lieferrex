package com.htlimst.lieferrex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "oeffnungszeit")
public class Oeffnungszeit {

    @Id
    @Column(name = "oeffnungszeit_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer tag;
    private Time oeffnungszeit;
    private Time startpause;
    private Time endepause;
    private Time schliessungszeit;






}
