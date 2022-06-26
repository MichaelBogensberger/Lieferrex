package com.htlimst.lieferrex.controller.zustellung;

import com.htlimst.lieferrex.model.Kunde;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ZubereitungsModel {

    HashMap<String, Integer> gerichtBestellungModelList;
    int counter;
    long id;
    Kunde kunde;

    public ZubereitungsModel(HashMap<String, Integer> gerichtBestellungModelList, int counter, long id, Kunde kunde) {
        this.gerichtBestellungModelList = gerichtBestellungModelList;
        this.counter = counter;
        this.id = id;
        this.kunde = kunde;
    }
}
