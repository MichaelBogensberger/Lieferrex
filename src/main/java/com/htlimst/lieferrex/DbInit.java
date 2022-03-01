package com.htlimst.lieferrex;

import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


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




    @Override
    public void run(String... args) {

        Layout layout = new Layout(null, "Testlayout");
        layoutRepository.save(layout);


        Mandant mandant = new Mandant(null, "MandantenFirma", "Österreich", "Imst", 12345, "Straße", "10", 0650123123, 1234.5, 50000, "mandant@gmail.com", 7.5, 3.5, layout);
        mandantRepository.save(mandant);

        Kunde kunde = new Kunde(null, "Vorname", "Nachname", "kunde@gmail.com", this.passwordEncoder.encode("kunde1234"), "Imst", 12345, "Straße", "1", 430650123, "Österreich", true);
        this.kundeRepository.save(kunde);

        Kategorie kategorie = new Kategorie(null, "Türkisch");
        this.kategorieRepository.save(kategorie);

        Bestellart bestellart = new Bestellart(null, "Abholung");
        this.bestellartRepository.save(bestellart);

        Angestellter angestellter = new Angestellter(null, mandant, "Vorname", "Nachname", "angestellt@gmail.com", this.passwordEncoder.encode("angestellt1234"));
        this.angestellterRepository.save(angestellter);

        Bestellstatus bestellstatus = new Bestellstatus(null, "Abgeschlossen");
        this.bestellstatusRepository.save(bestellstatus);



    }
}
