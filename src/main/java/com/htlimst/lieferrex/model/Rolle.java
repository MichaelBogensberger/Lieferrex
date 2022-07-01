package com.htlimst.lieferrex.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rolle")
public class Rolle {

    @Id
    @Column(name = "rolle_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @JsonIgnore
    private String rolle;

    public Rolle(String rolle) {
        this.rolle = rolle;
    }

    public String getRolle() {
        return rolle;
    }
}
