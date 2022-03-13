package com.htlimst.lieferrex.controller.baukasten;

import java.util.List;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BaukastenController {

    @Autowired
    MandantServiceImpl mandantServiceImpl;

    @Autowired
    FragmentServiceImpl fragmentServiceImpl;

    @Autowired
    FragmentTextServiceImpl fragmentTextServiceImpl;

    // -- Fragments mit Model
    // fragment_header 	(titel, text, bild)
    // fragment_text		(titel, text)
    // fragment_bild		(image)

    // -- Fragments statisch (mit Model???)
    // fragment_map
    // fragment_bestellung
    // fragment_oeffnungszeiten
    // fragment_kontakt

    // -- Sites
    // Gallery
    // Ueber uns


    @GetMapping("/baukasten/{restaurant}")
    public String showBaukasten(Model model, @PathVariable String restaurant){


        // Get Mandant ueber Name in der URL
        Mandant mandant = mandantServiceImpl.findMandantByFirmenname(restaurant).get();

        // Alle Fragmente des Mandaten ueber dessen ID
        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());

        // Alle Fragmente mit Position der VIEW uebergeben
        for (Fragment fragment : fragments) {
            model.addAttribute(fragment.getPosition().getName(), fragment);
            if (fragment.getFragmenttype().getType().equals("map")) {
                model.addAttribute("gerichte", mandant.getGerichte());
            }
        }        
        
        // Layout des Mandanten der VIEW uebergeben
        model.addAttribute("layout", mandant.getLayout().getName());
        
        return "baukasten/frame.html";
    }

}
