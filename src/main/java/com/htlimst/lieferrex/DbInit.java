package com.htlimst.lieferrex;

import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.enums.BestellartEnum;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import com.htlimst.lieferrex.model.enums.KategorieEnum;
import com.htlimst.lieferrex.model.enums.WochentagEnum;
import com.htlimst.lieferrex.model.fragments.FragmentHeader;
import com.htlimst.lieferrex.model.fragments.FragmentMap;
import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.model.fragments.FragmentType;
import com.htlimst.lieferrex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        private FragmentHeaderRepository fragmentHeaderRepository;
        @Autowired
        private FragmentImageRepository fragmentImageRepository;
        @Autowired
        private FragmentMapRepository fragmentMapRepository;
        @Autowired
        private FragmentRepository fragmentRepository;
        @Autowired
        private FragmentTextRepository fragmentTextRepository;
        @Autowired
        private FragmentTypeRepository fragmentTypeRepository;
        @Autowired
        private GeoPositionRepository geoPositionRepository;
        @Autowired
        private GerichtBestellungRepository gerichtBestellungRepository;
        @Autowired
        private GerichtRepository gerichtRepository;
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
        private PositionRepository positionRepository;
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

                // Check if databse is empty or not

                if (mandantRepository.count() > 0) {
                        return;
                }

                deleteAll();

                LocalDateTime localDateTime = LocalDateTime.now();

                // |---------- Layouts
                ArrayList<Layout> layouts = new ArrayList<>();
                ArrayList<Layout> savedLayouts = new ArrayList<>();

                layouts.add(new Layout(null, "layoutEINS", null, null));
                layouts.add(new Layout(null, "layoutZWEI", null, null));
                layouts.add(new Layout(null, "layoutDREI", null, null));
                layouts.add(new Layout(null, "layoutVIER", null, null));

                for (Layout layout : layouts) {
                        layoutRepository.save(layout);
                        savedLayouts.add(layout);
                }

                // |---------- Positions
                ArrayList<Position> positions = new ArrayList<>();

                // Positions - Layout EINS
                positions.add(new Position(null, "r1c1", savedLayouts.get(0), null));
                positions.add(new Position(null, "r2c1", savedLayouts.get(0), null));
                positions.add(new Position(null, "r3c1", savedLayouts.get(0), null));
                positions.add(new Position(null, "r3c2", savedLayouts.get(0), null));

                // Positions - Layout ZWEI
                positions.add(new Position(null, "r1c1", savedLayouts.get(1), null));
                positions.add(new Position(null, "r2c1", savedLayouts.get(1), null));
                positions.add(new Position(null, "r2c2", savedLayouts.get(1), null));
                positions.add(new Position(null, "r3c1", savedLayouts.get(1), null));

                // Positions - Layout DREI
                positions.add(new Position(null, "r1c1", savedLayouts.get(2), null));
                positions.add(new Position(null, "r1c2", savedLayouts.get(2), null));
                positions.add(new Position(null, "r2c1", savedLayouts.get(2), null));
                positions.add(new Position(null, "r3c1", savedLayouts.get(2), null));
                positions.add(new Position(null, "r3c2", savedLayouts.get(2), null));

                // Positions - Layout VIER
                positions.add(new Position(null, "r1c1", savedLayouts.get(3), null));
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
                fragmentTypes.add(new FragmentType(null, "karte", null));
                fragmentTypes.add(new FragmentType(null, "image", null));
                fragmentTypes.add(new FragmentType(null, "zeiten", null));
                fragmentTypes.add(new FragmentType(null, "kontakt", null));
                fragmentTypes.add(new FragmentType(null, "header-1", null));
                fragmentTypes.add(new FragmentType(null, "header-2", null));
                fragmentTypes.add(new FragmentType(null, "header-3", null));
                fragmentTypes.add(new FragmentType(null, "header-4", null));

                for (FragmentType fragmentType : fragmentTypes) {
                        fragmentTypeRepository.save(fragmentType);
                }

                Bestellart bestellart = new Bestellart(null, BestellartEnum.ABHOLUNG);
                Bestellart bestellart1 = new Bestellart(null, BestellartEnum.LIEFERUNG);
                this.bestellartRepository.save(bestellart);
                this.bestellartRepository.save(bestellart1);

                GeoPosition geoPosition = new GeoPosition(null, 47.2340197, 10.7398698);

                GeoPosition geoPosition2 = new GeoPosition(null, 47.2240851, 10.7458147);

                geoPositionRepository.save(geoPosition);
                geoPositionRepository.save(geoPosition2);

                Set<Bestellart> mandantBestellart = new HashSet<Bestellart>();
                mandantBestellart.add(bestellart);
                Mandant mandant = new Mandant(null, "Asia Royal", "Österreich", "Zell am See", "5700",
                                "Kitzsteinhornstraße",
                                "33", "0654255264", 41.0, 50000, 15, 30, "mandant1@business.example.com", 10.0, 0, null,
                                mandantBestellart,
                                layouts.get(0), geoPosition);
                Mandant mandant2 = new Mandant(null, "Asiamix", "Österreich", "Gemeinde Imst", "6460",
                                "Franz-Xaver-Renn-Straße", "4", "0650123123", 0, 50000, 15, 30, "mandant2@business.example.com",
                                7.5, 3.5, null,
                                mandantBestellart, layouts.get(1), geoPosition2);

                Kategorie kategorie = new Kategorie(null, KategorieEnum.FINE_DINING);
                kategorieRepository.save(kategorie);
                Kategorie kategorie1 = new Kategorie(null, KategorieEnum.CASUAL_DINING);
                kategorieRepository.save(kategorie1);
                Kategorie kategorie2 = new Kategorie(null, KategorieEnum.FAMILY_STYLE);
                kategorieRepository.save(kategorie2);
                Kategorie kategorie3 = new Kategorie(null, KategorieEnum.FAST_FOOD);
                kategorieRepository.save(kategorie3);
                Kategorie kategorie4 = new Kategorie(null, KategorieEnum.BUFFET);
                kategorieRepository.save(kategorie4);
                Kategorie kategorie5 = new Kategorie(null, KategorieEnum.OTHER);
                kategorieRepository.save(kategorie5);

                mandant.setKategorie(kategorie);
                mandantRepository.save(mandant);
                mandant2.setKategorie(kategorie);
                mandantRepository.save(mandant2);

                // |---------- Fragments

                // HIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRRHIERRRRRRRR

                ArrayList<Fragment> fragments = new ArrayList<>();

                fragments.add(new Fragment(null, positions.get(0), mandant, fragmentTypes.get(6), null, null, null,
                                null));

                for (Fragment fragment : fragments) {
                        fragmentRepository.save(fragment);
                }

                // |--------- FragmentHeaders
                ArrayList<FragmentHeader> fragmentheaders = new ArrayList<>();

                fragmentheaders.add(
                                new FragmentHeader(null, "Cooler Titel Eins", "Cooler Text des ersten Fragments", null,
                                                fragments.get(0)));

                for (FragmentHeader fragmentHeader : fragmentheaders) {
                        fragmentHeaderRepository.save(fragmentHeader);
                }

                Kunde kunde = new Kunde(null, "Michael", "Bogensberger", "kunde01@example.com",
                                this.passwordEncoder.encode("123"), "Salzburg", "5026", "Ziegelstadelstraße", "6a",
                                "06123456789",
                                "Österreich", true);
                this.kundeRepository.save(kunde);
                Kunde kunde2 = new Kunde(null, "Julian", "Meilinger", "kunde02@example.com",
                                this.passwordEncoder.encode("123"), "Neukirchen am Großvenediger", "5741",
                                "Geiernestgassl", "8",
                                "065123456789", "Österreich", false);
                this.kundeRepository.save(kunde2);
                Kunde kunde3 = new Kunde(null, "Max", "Mustermann", "kunde03@example.com",
                                this.passwordEncoder.encode("123"),
                                "Neukirchen am Großvenediger", "5741", "Geiernestgassl", "8", "065123456789",
                                "Österreich", false);
                this.kundeRepository.save(kunde3);

                Bestellstatus bestellstatus = new Bestellstatus(null, BestellstatusEnum.IN_ZUBEREITUNG, null);
                this.bestellstatusRepository.save(bestellstatus);
                this.bestellstatusRepository.save(new Bestellstatus(null, BestellstatusEnum.FERTIG_ZUM_ABHOLEN, null));
                this.bestellstatusRepository.save(new Bestellstatus(null, BestellstatusEnum.IN_AUSLIEFERUNG, null));
                this.bestellstatusRepository.save(new Bestellstatus(null, BestellstatusEnum.ABGESCHLOSSEN, null));

                Set<Bestellstatus> bestellstatis = new HashSet<Bestellstatus>();
                bestellstatis.add(bestellstatus);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                Bestellung bestellung = new Bestellung(null, 10, timestamp, 15.5, 2.5, bestellart, kunde, mandant,
                                bestellstatus, 1);
                Bestellung bestellung2 = new Bestellung(null, 20, timestamp, 20.5, 0, bestellart, kunde, mandant,
                                bestellstatus, 1);

                this.bestellungRepository.save(bestellung);
                this.bestellungRepository.save(bestellung2);

                Gericht gericht = new Gericht(null, "Wiener Schnitzel", "Paniertes Kalbsschnitzel mit Kartoffelsalat",
                                10.50,
                                0.0, 100, 1, mandant);
                this.gerichtRepository.save(gericht);
                Gericht gericht2 = new Gericht(null, "Döner", "Döner mit Mangosoße", 5.50, 1.0, 150, 2, mandant);
                this.gerichtRepository.save(gericht2);
                Gericht gericht3 = new Gericht(null, "Pizza", "Pizza mit Lachs", 25.00, 0.0, 5, 0, mandant);
                this.gerichtRepository.save(gericht3);

                GerichtBestellung gerichtBestellung = new GerichtBestellung(null, "Extra Preiselbeeren", gericht,
                                bestellung);
                GerichtBestellung gerichtBestellung1 = new GerichtBestellung(null, "Viel Fleisch", gericht2,
                                bestellung2);
                GerichtBestellung gerichtBestellung2 = new GerichtBestellung(null, "Mit Knoblauchsoße", gericht3,
                                bestellung2);
                this.gerichtBestellungRepository.save(gerichtBestellung);
                this.gerichtBestellungRepository.save(gerichtBestellung1);
                this.gerichtBestellungRepository.save(gerichtBestellung2);

                Rolle rolle1 = new Rolle(null, "ROLE_KUNDE");
                Rolle rolle2 = new Rolle(null, "ROLE_MANDANT");
                Rolle rolle3 = new Rolle(null, "ROLE_ANGESTELLTER");
                this.rolleRepository.save(rolle1);
                this.rolleRepository.save(rolle2);
                this.rolleRepository.save(rolle3);

                Set<Rolle> angestellerRollen = new HashSet<Rolle>();
                angestellerRollen.add(rolle3);
                String token = UUID.randomUUID().toString();
                Angestellter angestellter = new Angestellter(null, mandant, "Niklas", "Heim", "angestellt@gmail.com",
                                this.passwordEncoder.encode("123"), token, angestellerRollen);
                this.angestellterRepository.save(angestellter);
                String token1 = UUID.randomUUID().toString();
                Angestellter angestellter1 = new Angestellter(null, mandant2, "Liuming", "Xia",
                                "angestellter@gmail.com",
                                this.passwordEncoder.encode("123"), token1, angestellerRollen);
                this.angestellterRepository.save(angestellter1);

                Oeffnungszeit oeffnungszeit = new Oeffnungszeit(null, WochentagEnum.MONDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant);
                this.oeffnungszeitRepository.save(oeffnungszeit);
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.TUESDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.WEDNESDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.THURSDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.FRIDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.SATURDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.SUNDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));

                Oeffnungszeit oeffnungszeit2 = new Oeffnungszeit(null, WochentagEnum.MONDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant2);
                this.oeffnungszeitRepository.save(oeffnungszeit);
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.TUESDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant2));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.WEDNESDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant2));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.THURSDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant2));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.FRIDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant2));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.SATURDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant2));
                this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.SUNDAY, new Time(10, 0, 0),
                                new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant2));

                Umsatz umsatz = new Umsatz(null, localDateTime.getMonthValue(), localDateTime.getYear(), 41.0, mandant);
                this.umsatzRepository.save(umsatz);
                Umsatz umsatz2 = new Umsatz(null, 5, 2022, 1903.40, mandant2);
                this.umsatzRepository.save(umsatz2);

                Seitenaufrufe seitenaufrufe = new Seitenaufrufe(null, 3, 2022, 666, mandant);
                this.seitenaufrufeRepository.save(seitenaufrufe);
                Seitenaufrufe seitenaufrufe1 = new Seitenaufrufe(null, 3, 2022, 5410, mandant2);
                this.seitenaufrufeRepository.save(seitenaufrufe1);

        }

        private void deleteAll() {
                umsatzRepository.deleteAll();
                seitenaufrufeRepository.deleteAll();
                gerichtBestellungRepository.deleteAll();
                gerichtRepository.deleteAll();
                bestellungRepository.deleteAll();
                angestellterRepository.deleteAll();
                rolleRepository.deleteAll();
                oeffnungszeitRepository.deleteAll();
                fragmentHeaderRepository.deleteAll();
                fragmentImageRepository.deleteAll();
                fragmentMapRepository.deleteAll();
                fragmentRepository.deleteAll();
                fragmentTextRepository.deleteAll();
                kundeRepository.deleteAll();
                fragmentTypeRepository.deleteAll();
                positionRepository.deleteAll();
                mandantRepository.deleteAll();
                layoutRepository.deleteAll();
                kategorieRepository.deleteAll();
                bestellartRepository.deleteAll();
                bestellstatusRepository.deleteAll();
                geoPositionRepository.deleteAll();
        }

}