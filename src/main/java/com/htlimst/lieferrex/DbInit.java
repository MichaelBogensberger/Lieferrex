package com.htlimst.lieferrex;

import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.fragments.FragmentMap;
import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.model.fragments.FragmentType;
import com.htlimst.lieferrex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@Service
public class DbInit implements CommandLineRunner {
    @Autowired
    private AngestellterRepository angestellterRepository;
    @Autowired
    private BestellartRepository bestellartRepository;
    @Autowired
    private BestellstatusRepository bestellstatusRepository;
    @Autowired
    private BestellungRepository bestellungRepository;
    @Autowired
    private FragmentMapRepository fragmentMapRepository;
    @Autowired
    private FragmentRepository fragmentRepository;
    @Autowired
    private FragmentTextRepository fragmentTextRepository;
    @Autowired
    private GerichtRepository gerichtRepository;
    @Autowired
    private GerichtBestellungRepository gerichtBestellungRepository;
    @Autowired
    private KategorieRepository kategorieRepository;
    @Autowired
    private KundeRepository kundeRepository;
    @Autowired
    private LayoutRepository layoutRepository;
    @Autowired
    private MandantRepository mandantRepository;
    @Autowired
    private OeffnungszeitRepository oeffnungszeitRepository;
    @Autowired
    private RolleRepository rolleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsatzRepository umsatzRepository;
    @Autowired
    private SeitenaufrufeRepository seitenaufrufeRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private FragmentTypeRepository fragmentTypeRepository;






    @Override
    public void run(String... args) {
/*
        deleteAll();

        // |---------- Layouts
        ArrayList<Layout> layouts = new ArrayList<>();
        ArrayList<Layout> savedLayouts = new ArrayList<>();
        
        layouts.add(new Layout(null, "layoutEINS"));
        layouts.add(new Layout(null, "layoutZWEI"));
        layouts.add(new Layout(null, "layoutDREI"));
        layouts.add(new Layout(null, "layoutVIER"));
        
        for (Layout layout : layouts) {
            layoutRepository.save(layout);
            savedLayouts.add(layout);
        }

        // |---------- Positions
        ArrayList<Position> positions = new ArrayList<>();

        // Positions - Layout EINS
        positions.add(new Position(null, "r1c1", savedLayouts.get(0), null));
        positions.add(new Position(null, "r2c1", savedLayouts.get(0), null));
        positions.add(new Position(null, "r2c2", savedLayouts.get(0), null));
        
        // Positions - Layout ZWEI
        positions.add(new Position(null, "r1c1", savedLayouts.get(1), null));
        positions.add(new Position(null, "r2c1", savedLayouts.get(1), null));
        positions.add(new Position(null, "r3c1", savedLayouts.get(1), null));
        positions.add(new Position(null, "r3c2", savedLayouts.get(1), null));
        
        // Positions - Layout DREI
        positions.add(new Position(null, "r1c1", savedLayouts.get(2), null));
        positions.add(new Position(null, "r2c1", savedLayouts.get(2), null));
        positions.add(new Position(null, "r2c2", savedLayouts.get(2), null));
        positions.add(new Position(null, "r3c1", savedLayouts.get(2), null));
        
        // Positions - Layout VIER
        positions.add(new Position(null, "r1c1", savedLayouts.get(3), null));
        positions.add(new Position(null, "r1c2", savedLayouts.get(3), null));
        positions.add(new Position(null, "r2c1", savedLayouts.get(3), null));
        positions.add(new Position(null, "r3c1", savedLayouts.get(3), null));
        positions.add(new Position(null, "r3c2", savedLayouts.get(3), null));
        
        for (Position position : positions) {
            positionRepository.save(position);
        }

        // |---------- FragmentTypes

        ArrayList<FragmentType> fragmentTypes = new ArrayList<>();

        fragmentTypes.add(new FragmentType(null, "text", null));
        fragmentTypes.add(new FragmentType(null, "map", null));

        for (FragmentType fragmentType : fragmentTypes) {
            fragmentTypeRepository.save(fragmentType);
        }
        
        Bestellart bestellart = new Bestellart(null, "Abholung");
        this.bestellartRepository.save(bestellart);
        
        
        Set mandantBestellart = new HashSet();
        mandantBestellart.add(bestellart);
        Mandant mandant = new Mandant(null, "MandantenFirma", "Österreich", "Imst", 12345, "Straße", "10", "0650123123", 1234.5, 50000, "mandant@gmail.com", 7.5, 3.5, null, mandantBestellart, layouts.get(0));
        Mandant mandant2 = new Mandant(null, "MandantenFirma2", "Österreich", "Imst", 12345, "Straße", "10", "0650123123", 1234.5, 50000, "mandant1@gmail.com", 7.5, 3.5, null, mandantBestellart, layouts.get(1));
               
        
        Kategorie kategorie = new Kategorie();
        kategorie.setName("Fine Dining");
        kategorieRepository.save(kategorie);
        
        mandant.setKategorie(kategorie);
        mandantRepository.save(mandant);
        mandant2.setKategorie(kategorie);
        mandantRepository.save(mandant2);
        
        // |---------- Fragments 
        ArrayList<Fragment> fragments = new ArrayList<>();
        
        fragments.add(new Fragment(null, positions.get(0), mandant, fragmentTypes.get(0), null, null));
        fragments.add(new Fragment(null, positions.get(1), mandant, fragmentTypes.get(1), null, null));
        fragments.add(new Fragment(null, positions.get(2), mandant, fragmentTypes.get(0), null, null));
        
        fragments.add(new Fragment(null, positions.get(3), mandant2, fragmentTypes.get(0), null, null));
        fragments.add(new Fragment(null, positions.get(4), mandant2, fragmentTypes.get(1), null, null));
        fragments.add(new Fragment(null, positions.get(5), mandant2, fragmentTypes.get(0), null, null));
        fragments.add(new Fragment(null, positions.get(6), mandant2, fragmentTypes.get(0), null, null));

        for (Fragment fragment : fragments) {
            fragmentRepository.save(fragment);
        }
        
        // |--------- FragmentTexts
        ArrayList<FragmentText> fragmenttexts = new ArrayList<>();

        fragmenttexts.add(new FragmentText(null, "Cooler Titel Eins", "Cooler Text des ersten Fragments", "#00ff00", fragments.get(0)));
        fragmenttexts.add(new FragmentText(null, "Cooler Titel Drei", "Cooler Text des dritten Fragments", "#00ff00", fragments.get(2)));

        fragmenttexts.add(new FragmentText(null, "Cooler Titel Eins", "Cooler Text des ersten Fragments", "#00ff00", fragments.get(3)));
        fragmenttexts.add(new FragmentText(null, "Cooler Titel Drei", "Cooler Text des dritten Fragments", "#00ff00", fragments.get(5)));
        fragmenttexts.add(new FragmentText(null, "Cooler Titel Vier", "Cooler Text des vierten Fragments", "#00ff00", fragments.get(6)));
        
        for (FragmentText fragmentText : fragmenttexts) {
            fragmentTextRepository.save(fragmentText);
        }

        // |--------- FragmentMaps
        ArrayList<FragmentMap> fragmentmaps = new ArrayList<>();

        fragmentmaps.add(new FragmentMap(null, "123456", "987654", fragments.get(1)));
        fragmentmaps.add(new FragmentMap(null, "123456", "987654", fragments.get(4)));
        
        for (FragmentMap fragmentMap : fragmentmaps) {
            fragmentMapRepository.save(fragmentMap);
        }


        Kunde kunde = new Kunde(null, "Vorname", "Nachname", "kunde@gmail.com", this.passwordEncoder.encode("123"), "Imst", 12345, "Straße", "1", "430650123", "Österreich", true);
        this.kundeRepository.save(kunde);


        Bestellstatus bestellstatus = new Bestellstatus(null, "Abgeschlossen");
        this.bestellstatusRepository.save(bestellstatus);

        Set bestellstatis = new HashSet();
        bestellstatis.add(bestellstatus);
        Bestellung bestellung = new Bestellung(null, 10, new Timestamp(2022, 3, 1, 15, 15, 0, 0), 15.5, 2.5, bestellart, kunde, mandant, bestellstatis);
        this.bestellungRepository.save(bestellung);

        Gericht gericht = new Gericht(null, "Wiener Schnitzel", "Paniertes Kalbsschnitzel mit Kartoffelsalat", 10.50, 0.0, 100, 1, mandant);
        this.gerichtRepository.save(gericht);
        Gericht gericht2 = new Gericht(null, "Döner", "Döner mit Mangosoße", 5.50, 1.0, 150, 2, mandant);
        this.gerichtRepository.save(gericht2);
        Gericht gericht3 = new Gericht(null, "Pizza", "Pizza mit Lachs", 25.00, 0.0, 5, 0, mandant);
        this.gerichtRepository.save(gericht3);

        GerichtBestellung gerichtBestellung = new GerichtBestellung(null, "Extra Preiselbeeren", gericht, bestellung);
        this.gerichtBestellungRepository.save(gerichtBestellung);

        Rolle rolle1 = new Rolle(null, "ROLE_KUNDE");
        Rolle rolle2 = new Rolle(null, "ROLE_MANDANT");
        Rolle rolle3 = new Rolle(null, "ROLE_ANGESTELLTER");
        this.rolleRepository.save(rolle1);
        this.rolleRepository.save(rolle2);
        this.rolleRepository.save(rolle3);

        Set angestellerRollen = new HashSet();
        angestellerRollen.add(rolle3);
        Angestellter angestellter = new Angestellter(null, mandant, "Vorname", "Nachname", "angestellt@gmail.com", this.passwordEncoder.encode("123"), angestellerRollen);
        this.angestellterRepository.save(angestellter);

        Oeffnungszeit oeffnungszeit = new Oeffnungszeit(null, 1, new Time(10, 0, 0), new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant);
        this.oeffnungszeitRepository.save(oeffnungszeit);

        Umsatz umsatz = new Umsatz(null, 3, 2022, 22325.50, mandant);
        this.umsatzRepository.save(umsatz);

        Seitenaufrufe seitenaufrufe = new Seitenaufrufe(null, 3, 2022, 666, mandant);
        this.seitenaufrufeRepository.save(seitenaufrufe);

*/

    }


    private void deleteAll() {
        seitenaufrufeRepository.deleteAll();
        umsatzRepository.deleteAll();
        oeffnungszeitRepository.deleteAll();
        angestellterRepository.deleteAll();
        rolleRepository.deleteAll();
        gerichtBestellungRepository.deleteAll();
        gerichtRepository.deleteAll();
        bestellungRepository.deleteAll();
        bestellstatusRepository.deleteAll();
        kundeRepository.deleteAll();
        fragmentTextRepository.deleteAll();
        fragmentMapRepository.deleteAll();
        fragmentRepository.deleteAll();
        mandantRepository.deleteAll();
        kategorieRepository.deleteAll();
        bestellartRepository.deleteAll();
        positionRepository.deleteAll();
        positionRepository.deleteAll();
        layoutRepository.deleteAll();
    }

}
