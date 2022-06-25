package com.htlimst.lieferrex.model;

import com.htlimst.lieferrex.model.enums.WochentagEnum;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WochentagEnum tag;

    private Time oeffnungszeit;
    private Time startpause;
    private Time endepause;
    private Time schliessungszeit;

    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false)
    private Mandant mandant;




}
