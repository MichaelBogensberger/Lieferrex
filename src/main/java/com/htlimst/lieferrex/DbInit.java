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






    @Override
    public void run(String... args) {

        deleteAll();

        Layout layout = new Layout(null, "Testlayout");
        layoutRepository.save(layout);

        Bestellart bestellart = new Bestellart(null, "Abholung");
        this.bestellartRepository.save(bestellart);

        Kategorie kategorie = new Kategorie(null, "Türkisch");
        this.kategorieRepository.save(kategorie);

        Set mandantKategorie = new HashSet();
        mandantKategorie.add(kategorie);
        Set mandantBestellart = new HashSet();
        mandantBestellart.add(bestellart);
        Mandant mandant = new Mandant(null, "MandantenFirma", "Österreich", "Imst", 12345, "Straße", "10", 0650123123, 1234.5, 50000, "mandant@gmail.com", 7.5, 3.5, mandantKategorie, mandantBestellart, layout);
        mandantRepository.save(mandant);

        Kunde kunde = new Kunde(null, "Vorname", "Nachname", "kunde@gmail.com", this.passwordEncoder.encode("kunde1234"), "Imst", 12345, "Straße", "1", 430650123, "Österreich", true);
        this.kundeRepository.save(kunde);


        Bestellstatus bestellstatus = new Bestellstatus(null, "Abgeschlossen");
        this.bestellstatusRepository.save(bestellstatus);

        Set bestellstatis = new HashSet();
        bestellstatis.add(bestellstatus);
        Bestellung bestellung = new Bestellung(null, 10, new Timestamp(2022, 3, 1, 15, 15, 0, 0), 15.5, 2.5, bestellart, kunde, mandant, bestellstatis);
        this.bestellungRepository.save(bestellung);

        Gericht gericht = new Gericht(null, "Wiener Schnitzel", "Paniertes Kalbsschnitzel mit Kartoffelsalat", 10.50, 0.0, 15, 1, mandant);
        this.gerichtRepository.save(gericht);

        GerichtBestellung gerichtBestellung = new GerichtBestellung(null, "Extra Preiselbeeren", gericht, bestellung);
        this.gerichtBestellungRepository.save(gerichtBestellung);

        Rolle rolle1 = new Rolle(null, "ROLE_KUNDE");
        Rolle rolle2 = new Rolle(null, "ROLE_MANDANT");
        Rolle rolle3 = new Rolle(null, "ROLE_ANGESTELLTER");
        this.rolleRepository.save(rolle1);
        this.rolleRepository.save(rolle2);
        this.rolleRepository.save(rolle3);

        Set angestellerRollen = new HashSet();
        angestellerRollen.add(rolle1);
        Angestellter angestellter = new Angestellter(null, mandant, "Vorname", "Nachname", "angestellt@gmail.com", this.passwordEncoder.encode("angestellt1234"), angestellerRollen);
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
    }

}
