// package com.htlimst.lieferrex;
//
//
// import com.htlimst.lieferrex.model.*;
// import com.htlimst.lieferrex.model.fragments.FragmentHeader;
// import com.htlimst.lieferrex.model.fragments.FragmentMap;
// import com.htlimst.lieferrex.model.fragments.FragmentText;
// import com.htlimst.lieferrex.model.fragments.FragmentType;
// import com.htlimst.lieferrex.repository.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
//
// import java.sql.Time;
// import java.sql.Timestamp;
// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.Set;
// import java.util.UUID;
//
//
// @Service
// public class DbInit implements CommandLineRunner {
//     @Autowired
//     private AngestellterRepository angestellterRepository;
//     @Autowired
//     private BestellartRepository bestellartRepository;
//     @Autowired
//     private BestellstatusRepository bestellstatusRepository;
//     @Autowired
//     private BestellungRepository bestellungRepository;
//     @Autowired
//     private FragmentMapRepository fragmentMapRepository;
//     @Autowired
//     private FragmentRepository fragmentRepository;
//     @Autowired
//     private FragmentTextRepository fragmentTextRepository;
//     @Autowired
//     private GerichtRepository gerichtRepository;
//     @Autowired
//     private GerichtBestellungRepository gerichtBestellungRepository;
//     @Autowired
//     private KategorieRepository kategorieRepository;
//     @Autowired
//     private KundeRepository kundeRepository;
//     @Autowired
//     private LayoutRepository layoutRepository;
//     @Autowired
//     private MandantRepository mandantRepository;
//     @Autowired
//     private OeffnungszeitRepository oeffnungszeitRepository;
//     @Autowired
//     private RolleRepository rolleRepository;
//     @Autowired
//     private PasswordEncoder passwordEncoder;
//     @Autowired
//     private UmsatzRepository umsatzRepository;
//     @Autowired
//     private SeitenaufrufeRepository seitenaufrufeRepository;
//     @Autowired
//     private PositionRepository positionRepository;
//     @Autowired
//     private FragmentTypeRepository fragmentTypeRepository;
//     @Autowired
//     private FragmentHeaderRepository fragmentHeaderRepository;
//     @Autowired
//     private GeoPositionRepository geoPositionRepository;
//
//
//
//
//
//
//     @Override
//     public void run(String... args) {
//
//         deleteAll();
//
//         // |---------- Layouts
//         ArrayList<Layout> layouts = new ArrayList<>();
//         ArrayList<Layout> savedLayouts = new ArrayList<>();
//
//         layouts.add(new Layout(null, "layoutEINS"));
//         layouts.add(new Layout(null, "layoutZWEI"));
//         layouts.add(new Layout(null, "layoutDREI"));
//         layouts.add(new Layout(null, "layoutVIER"));
//
//         for (Layout layout : layouts) {
//             layoutRepository.save(layout);
//             savedLayouts.add(layout);
//         }
//
//         // |---------- Positions
//         ArrayList<Position> positions = new ArrayList<>();
//
//         // Positions - Layout EINS
//         positions.add(new Position(null, "r1c1", savedLayouts.get(0), null));
//         positions.add(new Position(null, "r2c1", savedLayouts.get(0), null));
//         positions.add(new Position(null, "r2c2", savedLayouts.get(0), null));
//
//         // Positions - Layout ZWEI
//         positions.add(new Position(null, "r1c1", savedLayouts.get(1), null));
//         positions.add(new Position(null, "r2c1", savedLayouts.get(1), null));
//         positions.add(new Position(null, "r3c1", savedLayouts.get(1), null));
//         positions.add(new Position(null, "r3c2", savedLayouts.get(1), null));
//
//         // Positions - Layout DREI
//         positions.add(new Position(null, "r1c1", savedLayouts.get(2), null));
//         positions.add(new Position(null, "r2c1", savedLayouts.get(2), null));
//         positions.add(new Position(null, "r2c2", savedLayouts.get(2), null));
//         positions.add(new Position(null, "r3c1", savedLayouts.get(2), null));
//
//         // Positions - Layout VIER
//         positions.add(new Position(null, "r1c1", savedLayouts.get(3), null));
//         positions.add(new Position(null, "r1c2", savedLayouts.get(3), null));
//         positions.add(new Position(null, "r2c1", savedLayouts.get(3), null));
//         positions.add(new Position(null, "r3c1", savedLayouts.get(3), null));
//         positions.add(new Position(null, "r3c2", savedLayouts.get(3), null));
//
//         for (Position position : positions) {
//             positionRepository.save(position);
//         }
//
//         // |---------- FragmentTypes
//
//         ArrayList<FragmentType> fragmentTypes = new ArrayList<>();
//
//         fragmentTypes.add(new FragmentType(null, "text", null));
//         fragmentTypes.add(new FragmentType(null, "map", null));
//         fragmentTypes.add(new FragmentType(null, "header", null));
//
//         for (FragmentType fragmentType : fragmentTypes) {
//             fragmentTypeRepository.save(fragmentType);
//         }
//
//         Bestellart bestellart = new Bestellart(null, "Abholung");
//         Bestellart bestellart1 = new Bestellart(null, "Lieferung");
//         this.bestellartRepository.save(bestellart);
//         this.bestellartRepository.save(bestellart1);
//
//         GeoPosition geoPosition = new GeoPosition(null, 47.2340197, 10.7398698);
//
//         GeoPosition geoPosition2 = new GeoPosition(null, 47.2240851, 10.7458147);
//
//         geoPositionRepository.save(geoPosition);
//         geoPositionRepository.save(geoPosition2);
//
//
//
//         Set<Bestellart> mandantBestellart = new HashSet<Bestellart>();
//         mandantBestellart.add(bestellart);
//         Mandant mandant = new Mandant(null, "MandantenFirma", "Österreich", "Imst", "6460", "Hinterseberweg", "12", "0650123123", 1234.5, 50000, "mandant@gmail.com", 7.5, 3.5, null, mandantBestellart, layouts.get(0), geoPosition);
//         Mandant mandant2 = new Mandant(null, "MandantenFirma2", "Österreich", "Imst", "12345", "Brennbichl", "25", "0650123123", 1234.5, 50000, "mandant1@gmail.com", 7.5, 3.5, null, mandantBestellart, layouts.get(1), geoPosition2);
//
//
//         Kategorie kategorie = new Kategorie(null, "fineDining");
//         kategorieRepository.save(kategorie);
//         Kategorie kategorie1 = new Kategorie(null, "casualDining");
//         kategorieRepository.save(kategorie1);
//         Kategorie kategorie2 = new Kategorie(null, "familyStyle");
//         kategorieRepository.save(kategorie2);
//         Kategorie kategorie3 = new Kategorie(null, "fastFood");
//         kategorieRepository.save(kategorie3);
//         Kategorie kategorie4 = new Kategorie(null, "buffet");
//         kategorieRepository.save(kategorie4);
//         Kategorie kategorie5 = new Kategorie(null, "other");
//         kategorieRepository.save(kategorie5);
//
//
//
//         mandant.setKategorie(kategorie);
//         mandantRepository.save(mandant);
//         mandant2.setKategorie(kategorie);
//         mandantRepository.save(mandant2);
//
//         // |---------- Fragments
//         ArrayList<Fragment> fragments = new ArrayList<>();
//
//         fragments.add(new Fragment(null, positions.get(0), mandant, fragmentTypes.get(2), null, null, null, null));
//         fragments.add(new Fragment(null, positions.get(1), mandant, fragmentTypes.get(1), null, null, null, null));
//         fragments.add(new Fragment(null, positions.get(2), mandant, fragmentTypes.get(0), null, null, null, null));
//
//         fragments.add(new Fragment(null, positions.get(3), mandant2, fragmentTypes.get(2), null, null, null, null));
//         fragments.add(new Fragment(null, positions.get(4), mandant2, fragmentTypes.get(1), null, null, null, null));
//         fragments.add(new Fragment(null, positions.get(5), mandant2, fragmentTypes.get(0), null, null, null, null));
//         fragments.add(new Fragment(null, positions.get(6), mandant2, fragmentTypes.get(0), null, null, null, null));
//
//         for (Fragment fragment : fragments) {
//             fragmentRepository.save(fragment);
//         }
//
//         // |--------- FragmentTexts
//         ArrayList<FragmentText> fragmenttexts = new ArrayList<>();
//
//         fragmenttexts.add(new FragmentText(null, "Cooler Titel Drei", "Cooler Text des dritten Fragments", "#00ff00", fragments.get(2)));
//
//         fragmenttexts.add(new FragmentText(null, "Cooler Titel Drei", "Cooler Text des dritten Fragments", "#00ff00", fragments.get(5)));
//         fragmenttexts.add(new FragmentText(null, "Cooler Titel Vier", "Cooler Text des vierten Fragments", "#00ff00", fragments.get(6)));
//
//         for (FragmentText fragmentText : fragmenttexts) {
//             fragmentTextRepository.save(fragmentText);
//         }
//
//         // |--------- FragmentHeaders
//         ArrayList<FragmentHeader> fragmentheaders = new ArrayList<>();
//
//         fragmentheaders.add(new FragmentHeader(null, "Cooler Titel Eins", "Cooler Text des ersten Fragments", "/restaurant/img1.png", fragments.get(0)));
//         fragmentheaders.add(new FragmentHeader(null, "Cooler Titel Eins", "Cooler Text des ersten Fragments", "/restaurant/img2.png", fragments.get(3)));
//
//         for (FragmentHeader fragmentHeader : fragmentheaders) {
//             fragmentHeaderRepository.save(fragmentHeader);
//         }
//
//         // |--------- FragmentMaps
//         ArrayList<FragmentMap> fragmentmaps = new ArrayList<>();
//
//         fragmentmaps.add(new FragmentMap(null, "123456", "987654", fragments.get(1)));
//         fragmentmaps.add(new FragmentMap(null, "123456", "987654", fragments.get(4)));
//
//         for (FragmentMap fragmentMap : fragmentmaps) {
//             fragmentMapRepository.save(fragmentMap);
//         }
//
//
//         Kunde kunde = new Kunde(null, "Vorname", "Nachname", "kunde@gmail.com", this.passwordEncoder.encode("123"), "Imst", "12345", "Straße", "1", "430650123", "Österreich", true);
//         this.kundeRepository.save(kunde);
//
//
//         Bestellstatus bestellstatus = new Bestellstatus(null, "Abgeschlossen");
//         this.bestellstatusRepository.save(bestellstatus);
//
//         Set<Bestellstatus> bestellstatis = new HashSet<Bestellstatus>();
//         bestellstatis.add(bestellstatus);
//         Bestellung bestellung = new Bestellung(null, 10, new Timestamp(2022, 3, 1, 15, 15, 0, 0), 15.5, 2.5, bestellart, kunde, mandant, bestellstatis);
//         this.bestellungRepository.save(bestellung);
//
//         Gericht gericht = new Gericht(null, "Wiener Schnitzel", "Paniertes Kalbsschnitzel mit Kartoffelsalat", 10.50, 0.0, 100, 1, mandant);
//         this.gerichtRepository.save(gericht);
//         Gericht gericht2 = new Gericht(null, "Döner", "Döner mit Mangosoße", 5.50, 1.0, 150, 2, mandant);
//         this.gerichtRepository.save(gericht2);
//         Gericht gericht3 = new Gericht(null, "Pizza", "Pizza mit Lachs", 25.00, 0.0, 5, 0, mandant);
//         this.gerichtRepository.save(gericht3);
//
//         GerichtBestellung gerichtBestellung = new GerichtBestellung(null, "Extra Preiselbeeren", gericht, bestellung);
//         this.gerichtBestellungRepository.save(gerichtBestellung);
//
//         Rolle rolle1 = new Rolle(null, "ROLE_KUNDE");
//         Rolle rolle2 = new Rolle(null, "ROLE_MANDANT");
//         Rolle rolle3 = new Rolle(null, "ROLE_ANGESTELLTER");
//         this.rolleRepository.save(rolle1);
//         this.rolleRepository.save(rolle2);
//         this.rolleRepository.save(rolle3);
//
//         Set<Rolle> angestellerRollen = new HashSet<Rolle>();
//         angestellerRollen.add(rolle3);
//         String token = UUID.randomUUID().toString();
//         Angestellter angestellter = new Angestellter(null, mandant, "Vorname", "Nachname", "angestellt@gmail.com", this.passwordEncoder.encode("123"), token , angestellerRollen);
//         this.angestellterRepository.save(angestellter);
//
//         Oeffnungszeit oeffnungszeit = new Oeffnungszeit(null, Wochentag.MONDAY, new Time(10, 0, 0), new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant);
//         this.oeffnungszeitRepository.save(oeffnungszeit);
//         this.oeffnungszeitRepository.save(new Oeffnungszeit(null, Wochentag.TUESDAY, new Time(10, 0, 0), new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
//         this.oeffnungszeitRepository.save(new Oeffnungszeit(null, Wochentag.WEDNESDAY, new Time(10, 0, 0), new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
//         this.oeffnungszeitRepository.save(new Oeffnungszeit(null, Wochentag.THURSDAY, new Time(10, 0, 0), new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
//         this.oeffnungszeitRepository.save(new Oeffnungszeit(null, Wochentag.FRIDAY, new Time(10, 0, 0), new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
//         this.oeffnungszeitRepository.save(new Oeffnungszeit(null, Wochentag.SATURDAY, new Time(10, 0, 0), new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
//         this.oeffnungszeitRepository.save(new Oeffnungszeit(null, Wochentag.SUNDAY, new Time(10, 0, 0), new Time(14, 0, 0), new Time(16, 0, 0), new Time(22, 0, 0), mandant));
//
//
//
//
//
//         Umsatz umsatz = new Umsatz(null, 3, 2022, 22325.50, mandant);
//         this.umsatzRepository.save(umsatz);
//
//         Seitenaufrufe seitenaufrufe = new Seitenaufrufe(null, 3, 2022, 666, mandant);
//         this.seitenaufrufeRepository.save(seitenaufrufe);
//
//
//     }
//
//
//     private void deleteAll() {
//         seitenaufrufeRepository.deleteAll();
//         umsatzRepository.deleteAll();
//         oeffnungszeitRepository.deleteAll();
//         angestellterRepository.deleteAll();
//         rolleRepository.deleteAll();
//         gerichtBestellungRepository.deleteAll();
//         gerichtRepository.deleteAll();
//         bestellungRepository.deleteAll();
//         bestellstatusRepository.deleteAll();
//         kundeRepository.deleteAll();
//         fragmentTextRepository.deleteAll();
//         fragmentMapRepository.deleteAll();
//         fragmentRepository.deleteAll();
//         mandantRepository.deleteAll();
//         kategorieRepository.deleteAll();
//         bestellartRepository.deleteAll();
//         positionRepository.deleteAll();
//         positionRepository.deleteAll();
//         layoutRepository.deleteAll();
//     }
//
// }
