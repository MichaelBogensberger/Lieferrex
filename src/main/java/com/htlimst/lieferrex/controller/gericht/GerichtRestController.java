package com.htlimst.lieferrex.controller.gericht;

import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.service.gericht.GerichtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class GerichtRestController {

    @Autowired
    GerichtService gerichtService;

    @RequestMapping("/api/gericht/{id}")
    public Optional<Gericht> apiGetGericht(@PathVariable("id") Long id) {
        return gerichtService.getGerichtById(id);
    }

    @PutMapping("/api/gericht/{id}")
    public Gericht updateUser(@RequestBody Gericht gericht, @PathVariable Long id) {
        return gerichtService.apiUpdateGericht(id, gericht);
    }



}
