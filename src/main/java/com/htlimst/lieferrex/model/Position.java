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
@Table(name = "position")
public class Position {

    @Id
    @Column(name = "position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    @ManyToOne
    @JoinColumn(name="template_id", nullable=false)
    private Template template;

    public String getPosition(){
        return this.position;
    }
}
