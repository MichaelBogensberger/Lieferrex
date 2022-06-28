package com.htlimst.lieferrex.controller;

import java.util.List;


import com.htlimst.lieferrex.dto.KundenIndexDto;
import com.htlimst.lieferrex.dto.MandantSuchDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.exceptions.MandantNotFoundException;

import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.KundeRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmentmap.FragmentMapServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.googleApi.GeocodingApi;
import com.htlimst.lieferrex.service.mandant.MandantService;

import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    private MandantRepository mandantRepository;
    private FragmentServiceImpl fragmentServiceImpl;
    private FragmentTextServiceImpl fragmenttextServiceImpl;
    private FragmentMapServiceImpl fragmentmapServiceImpl;
    private MandantService mandantService;
    private GeocodingApi geocodingApi;
    KundeRepository kundeRepository;

    @Autowired
    public MainController(MandantRepository mandantRepository, FragmentServiceImpl fragmentServiceImpl, FragmentTextServiceImpl fragmenttextServiceImpl, FragmentMapServiceImpl fragmentmapServiceImpl, MandantService mandantService, GeocodingApi geocodingApi, KundeRepository kundeRepository) {
        this.mandantRepository = mandantRepository;
        this.fragmentServiceImpl = fragmentServiceImpl;
        this.fragmenttextServiceImpl = fragmenttextServiceImpl;
        this.fragmentmapServiceImpl = fragmentmapServiceImpl;
        this.mandantService = mandantService;
        this.geocodingApi = geocodingApi;
        this.kundeRepository = kundeRepository;
    }



    @Value("${google.api.key}")
    String key;


    @GetMapping("")
    public String showIndexPage(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        if (principal == null){
            System.out.println("Index page loaded");
            return "main/index";
        }else {
            model.addAttribute("login", true);
            Kunde kunde = kundeRepository.findByEmail(principal.getUsername());
            KundenIndexDto kundenIndexDto = new KundenIndexDto().builder()
                    .kundenid(kunde.getId())
                    .kundenname(kunde.getVorname())
                    .StrasseHausnummer(kunde.getStrasse() + " " + kunde.getHausnummer())
                    .PlzOrt(kunde.getPlz() + " " + kunde.getOrt()).build();
            model.addAttribute("kunde", kundenIndexDto);
            return "main/index";
        }


    }

    @GetMapping("/seach")
    public String search(@RequestParam(value = "search", required = false) String adresse, RedirectAttributes redirectAttrs) {
        // Adresse zu PLZ umwandeln

        List<Mandant> mandanten = mandantRepository.findMandantByPlz(adresse);
        if (!mandanten.isEmpty()){
            return "redirect:restaurants/" + adresse;
        }

        try {
            String plz = geocodingApi.findPlzByAdresse(adresse);

            redirectAttrs.addAttribute("adresse", adresse);
            return "redirect:restaurants/" + plz;

        } catch (AdresseNotFoundException adresseNotFoundException) {
            System.out.println(adresseNotFoundException.getMessage());
            return "redirect:/";
        }


    }


    @GetMapping("/restaurants/{plz}")
    public String showRestaurants(@PathVariable String plz, @RequestParam(required = false) String geoeffnet, @RequestParam(required = false) String lieferkosten
            , @RequestParam(required = false) String mindestbestellwert, @RequestParam(required = false) String kategorie,
                                  @RequestParam(required = false) String adresse, Model model) {
        //Alle Restaurants der PLZ anzeigen
        System.out.println(plz);
        System.out.println(Boolean.parseBoolean(geoeffnet));
        double dLieferKosten = lieferkosten == null ? 0.0 : Double.parseDouble(lieferkosten);
        System.out.println(dLieferKosten);
        double dMindestbestellwert = mindestbestellwert == null ? 0.0 : Double.parseDouble(mindestbestellwert);
        System.out.println(dMindestbestellwert);

        System.out.println(kategorie);
        System.out.println(adresse);
        System.out.println("----------------------");


        try {
            List<MandantSuchDto> mandanten = mandantService.findMandantByPlz(plz, Boolean.parseBoolean(geoeffnet), dLieferKosten, dMindestbestellwert, kategorie);
            model.addAttribute("plz", plz);
            model.addAttribute("adresse", adresse);
            model.addAttribute("mandanten", mandanten);


            return "main/search";
        } catch (MandantNotFoundException mandantNotFoundException) {
            System.out.println(mandantNotFoundException.getMessage());
            model.addAttribute("error", "Es wurden keine Restaurants in der Umgebung gefunden!");
            return "main/search";
        }

    }




    @GetMapping("/buildOne")
    public String showBaukastenPage() {
        return "baukasten/one.html";
    }


    @GetMapping("/dashboard/kategorien")
    public String showDashboardKategorien() {
        return "dashboard/kategorien.html";
    }


    @GetMapping("/dashboard/oeffnungszeiten")
    public String showDashboardOeffnungszeiten() {
        return "dashboard/oeffnungszeiten.html";
    }

    @GetMapping("/dashboard/bewertungen")
    public String showDashboardBewertungen() {
        return "dashboard/bewertungen.html";
    }


    @GetMapping("/changeAddress")
    public String showChangeAddressPage() {
        return "main/changeaddress.html";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordPage() {
        return "main/changepassword.html";
    }


    

}
