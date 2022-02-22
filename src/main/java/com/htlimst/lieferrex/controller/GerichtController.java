package com.htlimst.lieferrex.controller;

import com.htlimst.lieferrex.service.gericht.GerichtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard/gericht")
public class GerichtController {

    @Autowired
    private GerichtService gerichtService;

    @DeleteMapping("{gericht_id}")
    public void gerichtLoeschen(@PathVariable("gericht_id") Long id){
        this.gerichtService.gerichtLoeschen(id);
    }

}