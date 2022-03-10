package com.htlimst.lieferrex.controller.baukasten;

import java.util.HashMap;
import java.util.List;
import com.htlimst.lieferrex.model.Fragment;
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


    @GetMapping("/baukasten/{restaurant}")
    public String showBaukasten(Model model, @PathVariable String restaurant){

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

        // Get Mandant ueber Name in der URL
        Mandant mandant = mandantServiceImpl.findMandantByFirmenname(restaurant).get();
        // Alle Fragmente des Mandaten ueber dessen ID
        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());

        // HashMap zum Uebertragen der Daten
        HashMap<String, String> map = new HashMap<>();

        map.put("layout", mandant.getLayout().getName());
        
        
        model.addAttribute("data", map);
        
        // for (Fragment fragment : fragments) {
            
        //     if(fragmentTextServiceImpl.findFragmenttextByFragment_id(fragment.getId()).isPresent()){
        //         System.out.println(fragment.getFragmenttext().getText() + "  |  " + fragment.getFragmenttext().getFarbe());
        //     } else if (fragmentmapServiceImpl.findFragmentmapByFragment_id(fragment.getId()).isPresent()){
        //         System.out.println(fragment.getFragmentmap().getLatitude() + "  |  " + fragment.getFragmentmap().getLongitude());
        //     }

        // }

        return "baukasten/frame.html";
    }

}
