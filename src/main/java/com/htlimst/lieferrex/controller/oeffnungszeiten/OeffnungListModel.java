package com.htlimst.lieferrex.controller.oeffnungszeiten;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OeffnungListModel {
    private List<OeffnungsDarstellungModel> list;

    public void addZeit(OeffnungsDarstellungModel oeffnungsDarstellungModel){
        this.list.add(oeffnungsDarstellungModel);
    }

}
