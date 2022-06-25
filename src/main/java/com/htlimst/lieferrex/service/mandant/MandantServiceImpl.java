package com.htlimst.lieferrex.service.mandant;

import com.htlimst.lieferrex.dto.MandantRegistrationDto;
import com.htlimst.lieferrex.dto.MandantSuchDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.exceptions.MandantNotFoundException;
import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.enums.WochentagEnum;
import com.htlimst.lieferrex.repository.*;


import com.htlimst.lieferrex.service.googleApi.GeocodingApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Service
public class MandantServiceImpl implements MandantService {

    private AngestellterRepository angestellterRepository;
    private MandantRepository mandantRepository;
    private RolleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private GeoPositionRepository geoPositionRepository;
    private OeffnungszeitRepository oeffnungszeitRepository;
    private GeocodingApi geocodingApi;
    private KategorieRepository kategorieRepository;

    @Autowired
    public MandantServiceImpl(AngestellterRepository angestellterRepository, MandantRepository mandantRepository, RolleRepository roleRepository, PasswordEncoder passwordEncoder, GeoPositionRepository geoPositionRepository, OeffnungszeitRepository oeffnungszeitRepository, GeocodingApi geocodingApi, KategorieRepository kategorieRepository) {
        this.angestellterRepository = angestellterRepository;
        this.mandantRepository = mandantRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.geoPositionRepository = geoPositionRepository;
        this.oeffnungszeitRepository = oeffnungszeitRepository;
        this.geocodingApi = geocodingApi;
        this.kategorieRepository = kategorieRepository;
    }

    @Override
    public List<Mandant> alleMandanten() {
        return null;
    }

    @Override
    public Mandant mandantEinfuegen(Mandant mandant) {
        return null;
    }

    @Override
    public Mandant mandantMitId(Long id) {
        return mandantRepository.getById(id);
    }

    @Override
    public void mandantLoeschenMitId(Long id) {
    }

    @Override
    public Optional<Mandant> findMandantByFirmenname(String name) {
        return mandantRepository.findMandantByFirmenname(name);
    }

    @Override
    public Optional<Mandant> findMandantByAngestellterEmail(String email) {
        return mandantRepository.findMandantByAngestellte_Email(email);
    }

    @Override
    public Mandant listAll() {
        return mandantRepository.getById(3L);
    }

    @Override
    public void save(Mandant mandant) {
        mandantRepository.save(mandant);
    }


    @Override
    public boolean saveRegistrationDto(MandantRegistrationDto mandantRegistrationDto) {
        GeoPosition geoPosition;
        mandantRegistrationDto.setHausnummer(mandantRegistrationDto.getHausnummer().toUpperCase());

        try {
            geoPosition = geocodingApi.getGeodaten(mandantRegistrationDto.getLand(), mandantRegistrationDto.getOrt(), mandantRegistrationDto.getPlz(),
                    mandantRegistrationDto.getStrasse(), mandantRegistrationDto.getHausnummer());
        } catch (AdresseNotFoundException e) {
            System.out.println("Geodaten nicht gefunden");
            return false;
        }

        geoPositionRepository.save(geoPosition);

        Mandant mandant = new Mandant().builder().
                firmenname(mandantRegistrationDto.getFirmenname()).
                land(mandantRegistrationDto.getLand()).
                ort(mandantRegistrationDto.getOrt()).
                plz(mandantRegistrationDto.getPlz()).
                strasse(mandantRegistrationDto.getStrasse()).
                hausnummer(mandantRegistrationDto.getHausnummer()).
                telefonnummer(mandantRegistrationDto.getTelefonnummer()).
                email(mandantRegistrationDto.getEmail()).
                mindestbestellwert(mandantRegistrationDto.getMindestbestellwert()).
                lieferkosten(mandantRegistrationDto.getLieferkosten())
                .seitenaufrufe_summe(0)
                .umsatz_summe(0.0)
                .durchschnittsAbholZeit(15)
                .durchschnittsLieferZeit(30)
                .geoPosition(geoPosition).
                kategorie(kategorieRepository.getById(6L)).build();
        mandantRepository.save(mandant);


        Angestellter angestellter = new Angestellter().builder().
                mandant(mandant).
                vorname(mandantRegistrationDto.getVorname()).
                nachname(mandantRegistrationDto.getNachname()).
                email(mandantRegistrationDto.getEmail()).
                passwort(passwordEncoder.encode(mandantRegistrationDto.getPasswort())).
                rolle(Arrays.asList(roleRepository.findByRolle("ROLE_MANDANT"))).build();
        angestellterRepository.save(angestellter);

        return true;
    }


    @Override
    public List<MandantSuchDto> findMandantByPlz(String Adresse, boolean isGeöffnet, double lieferKosten, double mindestbestellwert, String kategorie) throws MandantNotFoundException {
        List<Mandant> mandantenList = mandantRepository.findMandantByPlz(Adresse);
        List<Mandant> filteredMandantenList = new ArrayList<>();
        Stream<Mandant> mandantStream;

        if (mandantenList.isEmpty()) {
            throw new MandantNotFoundException();
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDate currentDate = LocalDate.now();
        WochentagEnum currentDay = WochentagEnum.valueOf(String.valueOf(currentDate.getDayOfWeek()));

        long now = System.currentTimeMillis();
        LocalTime currentTime = LocalTime.now();
        System.out.println(currentTime);


        List<MandantSuchDto> mandantSuchDtoList = new ArrayList<>();
        for (Mandant mandant : mandantenList) {
            boolean open = true;
            Optional<Oeffnungszeit> optHeutigeOeffungszeit = null;

            optHeutigeOeffungszeit = oeffnungszeitRepository.findOeffnungszeitsByMandantAndTag(mandant, currentDay);
            if (optHeutigeOeffungszeit.isPresent()) {
                Oeffnungszeit heutigeOeffungszeit = optHeutigeOeffungszeit.get();

                open = open && currentTime.isAfter(heutigeOeffungszeit.getOeffnungszeit().toLocalTime());
                System.out.println(open + " Is open");

                System.out.println(heutigeOeffungszeit.getSchliessungszeit().toLocalTime());
                System.out.println(currentTime);
                open = open && currentTime.isBefore(heutigeOeffungszeit.getSchliessungszeit().toLocalTime());
                System.out.println(open + " Is open");


                if (heutigeOeffungszeit.getStartpause() != null && heutigeOeffungszeit.getStartpause() != null) {
                    open = open && (currentTime.isBefore(heutigeOeffungszeit.getStartpause().toLocalTime()) || currentTime.isAfter(heutigeOeffungszeit.getEndepause().toLocalTime()));
                    System.out.println(open + " Is open");

                }
            } else {
                System.out.println("tag nicht gefunden");
                open = false;
            }


            if (lieferKosten != 0.0 && lieferKosten < mandant.getLieferkosten()) {
                System.out.println(mandant.getFirmenname() + " liefer----");
                continue;
            }
            if (mindestbestellwert != 0.0 && mindestbestellwert < mandant.getMindestbestellwert()) {
                System.out.println(mandant.getFirmenname() + " mind----");
                continue;
            }
            if (kategorie != null && !kategorie.equals(mandant.getKategorie().getName().toString())) {
                System.out.println(mandant.getFirmenname() + " mind----");
                continue;
            }
            if (isGeöffnet && !open) {
                continue;
            }

            mandantSuchDtoList.add(new MandantSuchDto().builder()
                    .id(mandant.getId())
                    .firmenname(mandant.getFirmenname())
                    .ort(mandant.getOrt())
                    .adresse(mandant.getStrasse() + " " + mandant.getHausnummer())
                    .rating(3)
                    .geöffnet(open)
                    .build());
        }
        return mandantSuchDtoList;
    }


}
