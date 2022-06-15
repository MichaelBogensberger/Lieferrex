package com.htlimst.lieferrex.controller.zubereitung;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class BestellungModel {

    HashMap<String, Integer> gerichtBestellungModelList;
    String timestamp;
    String zusatinfos;

    public BestellungModel(HashMap<String, Integer> gerichtBestellungModelList, String time, String zusatz) {

        this.gerichtBestellungModelList = gerichtBestellungModelList;
        this.timestamp = time;
        this.zusatinfos = zusatz;
    }
}
