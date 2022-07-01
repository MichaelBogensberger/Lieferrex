package com.htlimst.lieferrex.service.mandant;

import com.htlimst.lieferrex.dto.GeoPositionDto;
import com.htlimst.lieferrex.dto.MandantRegistrationDto;
import com.htlimst.lieferrex.dto.MandantSuchDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.exceptions.MandantNotFoundException;
import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.enums.KategorieEnum;
import com.htlimst.lieferrex.model.enums.WochentagEnum;
import com.htlimst.lieferrex.repository.*;


import com.htlimst.lieferrex.service.googleApi.GeocodingApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
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
    private SeitenaufrufeRepository seitenaufrufeRepository;
    private UmsatzRepository umsatzRepository;

    @Autowired
    public MandantServiceImpl(AngestellterRepository angestellterRepository, MandantRepository mandantRepository,
                              RolleRepository roleRepository, PasswordEncoder passwordEncoder, GeoPositionRepository geoPositionRepository,
                              OeffnungszeitRepository oeffnungszeitRepository, GeocodingApi geocodingApi, KategorieRepository kategorieRepository,
                              SeitenaufrufeRepository seitenaufrufeRepository, UmsatzRepository umsatzRepository) {
        this.angestellterRepository = angestellterRepository;
        this.mandantRepository = mandantRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.geoPositionRepository = geoPositionRepository;
        this.oeffnungszeitRepository = oeffnungszeitRepository;
        this.geocodingApi = geocodingApi;
        this.kategorieRepository = kategorieRepository;
        this.seitenaufrufeRepository = seitenaufrufeRepository;
        this.umsatzRepository = umsatzRepository;
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
    public Optional<Mandant> findMandantById(Long id) {
        return mandantRepository.findMandantById(id);
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
        GeoPositionDto geoPositionDto;
        GeoPosition geoPosition = new GeoPosition();
        mandantRegistrationDto.setHausnummer(mandantRegistrationDto.getHausnummer().toUpperCase());

        try {
            geoPositionDto = geocodingApi.getGeodaten(mandantRegistrationDto.getPlaceId());
        } catch (AdresseNotFoundException e) {
            System.out.println("Geodaten nicht gefunden");
            return false;
        }

        mandantRegistrationDto.setPlz(geoPositionDto.getPlz());

        geoPosition.setGeoLng(geoPositionDto.getLng());
        geoPosition.setGeoLat(geoPositionDto.getLat());

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
                .geoPosition(geoPosition)
                .kategorie(kategorieRepository.getKategorieByName(KategorieEnum.OTHER))
                .build();
        mandantRepository.save(mandant);


        Angestellter angestellter = new Angestellter().builder().
                mandant(mandant).
                vorname(mandantRegistrationDto.getVorname()).
                nachname(mandantRegistrationDto.getNachname()).
                email(mandantRegistrationDto.getEmail()).
                passwort(passwordEncoder.encode(mandantRegistrationDto.getPasswort())).
                rolle(Arrays.asList(roleRepository.findByRolle("ROLE_MANDANT"))).build();
        angestellterRepository.save(angestellter);


        this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.MONDAY, null, null, null, null, mandant));
        this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.TUESDAY, null, null, null, null, mandant));
        this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.WEDNESDAY, null, null, null, null, mandant));
        this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.THURSDAY, null, null, null, null, mandant));
        this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.FRIDAY, null, null, null, null, mandant));
        this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.SATURDAY, null, null, null, null, mandant));
        this.oeffnungszeitRepository.save(new Oeffnungszeit(null, WochentagEnum.SUNDAY, null, null, null, null, mandant));


        LocalDate current_date = LocalDate.now();
        int current_Year = current_date.getYear();
        int current_Month = current_date.getMonthValue();

        Seitenaufrufe seitenaufrufe = new Seitenaufrufe(null, current_Month, current_Year, 0, mandant);
        seitenaufrufeRepository.save(seitenaufrufe);

        Umsatz umsatz = new Umsatz(null, current_Month, current_Year, 0, mandant);
        umsatzRepository.save(umsatz);


        return true;
    }




    @Override
    public List<MandantSuchDto> findMandantByPlz(String Adresse, boolean isGeöffnet, double lieferKosten, double mindestbestellwert, String kategorie) throws MandantNotFoundException {
        List<Mandant> mandantenList = mandantRepository.findMandantByPlz(Adresse);

        if (mandantenList.isEmpty()) {
            throw new MandantNotFoundException();
        }

        LocalDate currentDate = LocalDate.now();
        WochentagEnum currentDay = WochentagEnum.valueOf(String.valueOf(currentDate.getDayOfWeek()));
        LocalTime currentTime = LocalTime.now();


        List<MandantSuchDto> mandantSuchDtoList = new ArrayList<>();
        for (Mandant mandant : mandantenList) {
            int bewertung;
            boolean open = isGeoeffnet(mandant, currentDay, currentTime);
            if (mandant.getBestellungen().size()==0){
                bewertung = 0;
            }else {
                bewertung = getBewertung(mandant);
            }


            if (lieferKosten != 0.0 && lieferKosten < mandant.getLieferkosten()) {
                continue;
            }
            if (mindestbestellwert != 0.0 && mindestbestellwert < mandant.getMindestbestellwert()) {
                continue;
            }
            if (kategorie != null && !kategorie.equals(mandant.getKategorie().getName().toString())) {
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
                    .rating(bewertung)
                    .geöffnet(open)
                    .build());
        }
        return mandantSuchDtoList;
    }


    @Override
    public boolean isGeoeffnet(Mandant mandant, WochentagEnum currentDay, LocalTime currentTime) {

        boolean open = true;
        Optional<Oeffnungszeit> optHeutigeOeffungszeit = null;

        optHeutigeOeffungszeit = oeffnungszeitRepository.findOeffnungszeitsByMandantAndTag(mandant, currentDay);
        if (optHeutigeOeffungszeit.isPresent()) {

            Oeffnungszeit heutigeOeffungszeit = optHeutigeOeffungszeit.get();
            open = open && currentTime.isAfter(heutigeOeffungszeit.getOeffnungszeit().toLocalTime());
            open = open && currentTime.isBefore(heutigeOeffungszeit.getSchliessungszeit().toLocalTime());


            if (heutigeOeffungszeit.getStartpause() != null && heutigeOeffungszeit.getStartpause() != null) {
                open = open && (currentTime.isBefore(heutigeOeffungszeit.getStartpause().toLocalTime()) || currentTime.isAfter(heutigeOeffungszeit.getEndepause().toLocalTime()));

            }
        } else {
            System.out.println("tag nicht gefunden");
            open = false;
        }
        return open;
    }


    @Override
    public int getBewertung(Mandant mandant) {
        double bewertungsSumme = 0.0;
        double anzahl = 0.0;

        for (Bestellung bestellung:mandant.getBestellungen()) {
            bewertungsSumme += bestellung.getBewertung();
            anzahl++;
        }
        int bewertung = (int) Math.round(bewertungsSumme/anzahl);
        return bewertung;
    }

}
