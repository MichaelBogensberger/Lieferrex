package com.htlimst.lieferrex.controller.baukasten;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.repository.PositionRepository;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BaukastenController {

    @Autowired
    MandantServiceImpl mandantServiceImpl;

    @Autowired
    FragmentServiceImpl fragmentServiceImpl;

    @Autowired
    FragmentTextServiceImpl fragmentTextServiceImpl;

    // TODO: Make model an Object, consistency

    @GetMapping("/restaurant/{restaurant}")
    public String showRestaurant(Model model, @PathVariable String restaurant){

        // TODO: Error-Handling

        // Get Mandant ueber Name in der URL
        Mandant mandant = mandantServiceImpl.findMandantByFirmenname(restaurant).get();

        // Alle Fragmente des Mandaten ueber dessen ID
        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());

        // Layout des Mandanten der VIEW uebergeben
        model.addAttribute("layout", mandant.getLayout().getName());

        // Alle Fragmente mit Position der VIEW uebergeben
        for (Fragment fragment : fragments) {
            model.addAttribute(fragment.getPosition().getName(), fragment);

            // Datenermittlung bei Ausgabe von Karte, Kontakt, etc.
            if (fragment.getFragmenttype().getType().equals("karte")) {
                model.addAttribute("gerichte", mandant.getGerichte());
            } else if(fragment.getFragmenttype().getType().equals("kontakt")){
                // TODO: Kontakte des Mandanten ausgeben
                model.addAttribute("kontakt", "getKontakt");
            }
        }
                
        return "baukasten/frame.html";
    }

    @GetMapping("/baukasten")
    public String showBaukasten(Model model){

        // TODO: Get Mandant by logged in User
        // Temporary Set Mandant
        Mandant mandant = mandantServiceImpl.findMandantByFirmenname("MandantenFirma2").get();

        model.addAttribute("layout", mandant.getLayout().getName());
        model.addAttribute("edit", true);

        // TODO: Make function, redundancy
        // Alle Fragmente des Mandaten ueber dessen ID
        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());

        // Layout des Mandanten der VIEW uebergeben
        model.addAttribute("layout", mandant.getLayout().getName());

        // Alle Fragmente mit Position der VIEW uebergeben
        for (Fragment fragment : fragments) {
            model.addAttribute(fragment.getPosition().getName(), fragment);

            // Datenermittlung bei Ausgabe von Karte, Kontakt, etc.
            if (fragment.getFragmenttype().getType().equals("karte")) {
                model.addAttribute("gerichte", mandant.getGerichte());
            } else if(fragment.getFragmenttype().getType().equals("kontakt")){
                // TODO: Kontakte des Mandanten ausgeben
                model.addAttribute("kontakt", "getKontakt");
            }
        }

        return "baukasten/frame";
    }

    // @GetMapping("/baukasten/module/{position}")
    // public String getModule(Model model, Authentication authentication, @PathVariable String position){
    //     Mandant mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName()).get();        
    //     Fragment fragment = fragmentServiceImpl.findFragmentByMandant_idAndPosition_name(mandant.getId(), position).get();

    //     model.addAttribute("content", fragment);

    //     return "baukasten/fragments/modules/" + fragment.getFragmenttype().getType();
    // }

}
