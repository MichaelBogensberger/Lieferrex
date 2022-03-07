package com.htlimst.lieferrex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Fragmenttext;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmentmap.FragmentMapServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.gericht.GerichtService;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    private MandantRepository mandantRepository;
    private MandantServiceImpl mandantServiceImpl;
    private FragmentServiceImpl fragmentServiceImpl;
    private FragmentTextServiceImpl fragmenttextServiceImpl;
    private FragmentMapServiceImpl fragmentmapServiceImpl;

    @Autowired
    private GerichtService gerichtService;

    public MainController(MandantRepository mandantRepository, MandantServiceImpl mandantServiceImpl, FragmentServiceImpl fragmentServiceImpl, FragmentTextServiceImpl fragmenttextServiceImpl, FragmentMapServiceImpl fragmentmapServiceImpl) {
        this.mandantRepository = mandantRepository;
        this.mandantServiceImpl = mandantServiceImpl;
        this.fragmentServiceImpl = fragmentServiceImpl;
        this.fragmenttextServiceImpl = fragmenttextServiceImpl;
        this.fragmentmapServiceImpl = fragmentmapServiceImpl;
    }

    @GetMapping("")
    public String showIndexPage() {
        System.out.println("Index page loaded");

        return "main/index";
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "main/search";
    }


    @GetMapping("/buildOne")
    public String showBaukastenPage() {
        return "baukasten/one.html";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard/dashboard.html";
    }

    @GetMapping("/dashboard/bestellungen")
    public String showDashboardBestellungen() {
        return "dashboard/bestellungen.html";
    }

    @GetMapping("/dashboard/zustellungen")
    public String showDashboardZustellungen() {
        return "dashboard/zustellung.html";
    }

    @GetMapping("/dashboard/kategorien")
    public String showDashboardKategorien() {
        return "dashboard/kategorien.html";
    }


    @GetMapping("/dashboard/benutzer")
    public String showDashboardBenutzer() {
        return "dashboard/benutzer.html";
    }

    @GetMapping("/dashboard/oeffnungszeiten")
    public String showDashboardOeffnungszeiten() {
        return "dashboard/oeffnungszeiten.html";
    }



    @GetMapping("/baukasten/{restaurant}")
    public String showBaukasten(Model model, @PathVariable String restaurant){
        // Get Mandant ueber Name in der URL
        Mandant mandant = mandantServiceImpl.findMandantByFirmenname(restaurant).get();
        // Alle Fragmente des Mandaten ueber dessen ID
        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());

        // HashMap zum Uebertragen der Daten
        HashMap<String, String> map = new HashMap<>();

        map.put("layout", mandant.getLayout().getName());

        // System.out.println(fragmenttextServiceImpl.findFragmenttextByFragment_id(1L).get().getText());
        
        for (Fragment fragment : fragments) {
            
            if(fragmenttextServiceImpl.findFragmenttextByFragment_id(fragment.getId()).isPresent()){
                System.out.println(fragment.getFragmenttext().getText() + "  |  " + fragment.getFragmenttext().getFarbe());
            } else if (fragmentmapServiceImpl.findFragmentmapByFragment_id(fragment.getId()).isPresent()){
                System.out.println(fragment.getFragmentmap().getLatitude() + "  |  " + fragment.getFragmentmap().getLongitude());
            }

        }

        model.addAttribute("data", map);

        return "baukasten/frame.html";
    }

}
