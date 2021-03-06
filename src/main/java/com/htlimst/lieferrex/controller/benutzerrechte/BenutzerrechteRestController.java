package com.htlimst.lieferrex.controller.benutzerrechte;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/benutzer/api")
public class BenutzerrechteRestController {

    private AngestellterService angestellterService;

    @Autowired
    public BenutzerrechteRestController(AngestellterService angestellterService){
        this.angestellterService = angestellterService;
    }

    @RequestMapping("/{id}")
    public Optional<Angestellter> apiGetAngestellter(@PathVariable Long id){
        return angestellterService.apiGetAngestellterById(id);
    }
}
