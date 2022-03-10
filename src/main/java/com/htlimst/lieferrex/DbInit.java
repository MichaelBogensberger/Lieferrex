package com.htlimst.lieferrex;

import com.htlimst.lieferrex.model.*;
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






    @Override
    public void run(String... args) {

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
        positions.add(new Position(null, "r1-c1", savedLayouts.get(0), null));
        positions.add(new Position(null, "r1-c1", savedLayouts.get(0), null));
        positions.add(new Position(null, "r2-c1", savedLayouts.get(0), null));
        positions.add(new Position(null, "r2-c2", savedLayouts.get(0), null));
        
        // Positions - Layout ZWEI
        positions.add(new Position(null, "r1-c1", savedLayouts.get(1), null));
        positions.add(new Position(null, "r2-c1", savedLayouts.get(1), null));
        positions.add(new Position(null, "r3-c1", savedLayouts.get(1), null));
        positions.add(new Position(null, "r3-c2", savedLayouts.get(1), null));
        
        // Positions - Layout DREI
        positions.add(new Position(null, "r1-c1", savedLayouts.get(2), null));
        positions.add(new Position(null, "r2-c1", savedLayouts.get(2), null));
        positions.add(new Position(null, "r2-c2", savedLayouts.get(2), null));
        positions.add(new Position(null, "r3-c1", savedLayouts.get(2), null));
        
        // Positions - Layout VIER
        positions.add(new Position(null, "r1-c1", savedLayouts.get(3), null));
        positions.add(new Position(null, "r1-c2", savedLayouts.get(3), null));
        positions.add(new Position(null, "r2-c1", savedLayouts.get(3), null));
        positions.add(new Position(null, "r3-c1", savedLayouts.get(3), null));
        positions.add(new Position(null, "r3-c2", savedLayouts.get(3), null));
        
        for (Position position : positions) {
            positionRepository.save(position);
        }

        Bestellart bestellart = new Bestellart(null, "Abholung");
        this.bestellartRepository.save(bestellart);


        Set mandantBestellart = new HashSet();
        mandantBestellart.add(bestellart);
        Mandant mandant = new Mandant(null, "MandantenFirma", "Österreich", "Imst", 12345, "Straße", "10", 0650123123, 1234.5, 50000, "mandant@gmail.com", 7.5, 3.5, null, mandantBestellart, layouts.get(1));

        Kategorie kategorie = new Kategorie();
        kategorie.setName("Fine Dining");
        kategorieRepository.save(kategorie);

        mandant.setKategorie(kategorie);
        mandantRepository.save(mandant);

        Kunde kunde = new Kunde(null, "Vorname", "Nachname", "kunde@gmail.com", this.passwordEncoder.encode("123"), "Imst", 12345, "Straße", "1", 430650123, "Österreich", true);
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
        mandantRepository.deleteAll();
        kategorieRepository.deleteAll();
        bestellartRepository.deleteAll();
        layoutRepository.deleteAll();
        positionRepository.deleteAll();
    }

}
