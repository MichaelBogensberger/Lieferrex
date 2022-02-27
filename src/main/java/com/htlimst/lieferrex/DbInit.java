package com.htlimst.lieferrex;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Rolle;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.repository.KundeRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.repository.RolleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Service
public class DbInit implements CommandLineRunner {
    private KundeRepository kundeRepository;
    private AngestellterRepository angestellterRepository;
    private MandantRepository mandantRepository;
    private RolleRepository rolleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DbInit(KundeRepository kundeRepository, AngestellterRepository angestellterRepository, MandantRepository mandantRepository, RolleRepository rolleRepository, PasswordEncoder passwordEncoder) {
        this.kundeRepository = kundeRepository;
        this.angestellterRepository = angestellterRepository;
        this.mandantRepository = mandantRepository;
        this.rolleRepository = rolleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {
        Kunde kunde;
        Angestellter angestellter;


        try {
            List<Kunde> kundeList = kundeRepository.getAllByEmail("kunde1@gmail.com");
            kundeRepository.deleteAll(kundeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Angestellter> angestellterList = angestellterRepository.getAllByEmail("angestellter@gmail.com");
            angestellterRepository.deleteAll(angestellterList);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Mandant mandant = mandantRepository.getById(1L);
        List<Rolle> rollen = Arrays.asList(rolleRepository.getById(2L), rolleRepository.getById(1L));

        // Crete users
        try {
            angestellter = new Angestellter(null, mandant, "Vorname", "Nachname", "angestellter@gmail.com", this.passwordEncoder.encode("ange1234"), rollen);
            this.angestellterRepository.save(angestellter);
            kunde = new Kunde(null, "Vorname", "Nachname", "kunde1@gmail.com", this.passwordEncoder.encode("kunde1234"), "Imst", 12345, "Straße", "1", 066411, "Österreich", true);
            this.kundeRepository.save(kunde);

        } catch (Exception e) {
            e.printStackTrace();
        }






    }
}
