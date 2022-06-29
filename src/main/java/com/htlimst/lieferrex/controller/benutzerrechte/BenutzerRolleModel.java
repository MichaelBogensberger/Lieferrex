package com.htlimst.lieferrex.controller.benutzerrechte;


import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Rolle;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BenutzerRolleModel {
    private Angestellter angestellter;
    private String rolle;

    public BenutzerRolleModel(Angestellter angestellter, String rolle) {
        this.angestellter = angestellter;
        this.rolle = rolle;
    }
}
