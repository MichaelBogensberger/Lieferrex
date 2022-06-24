package com.htlimst.lieferrex.controller.zubereitung;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Getter
@Setter
public class BestellungModel {

    HashMap<String, Integer> gerichtBestellungModelList;
    String timestamp;
    String zusatzinfos;
    int counter;

    public BestellungModel(HashMap<String, Integer> gerichtBestellungModelList, String time, String zusatz, int counter) {

        this.gerichtBestellungModelList = gerichtBestellungModelList;
        this.timestamp = time;
        this.zusatzinfos = zusatz;
        this.counter = counter;
    }
}
