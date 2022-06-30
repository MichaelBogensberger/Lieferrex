package com.htlimst.lieferrex.service.bestellung;

import com.htlimst.lieferrex.dto.*;
import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.enums.BestellartEnum;
import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
import com.htlimst.lieferrex.repository.*;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class BestellungServiceImpl implements BestellungService{

    private BestellungRepository bestellungRepository;
    private KundeRepository kundeRepository;
    private MandantRepository mandantRepository;
    private BestellstatusRepository bestellstatusRepository;
    private GerichtRepository gerichtRepository;
    private BestellartRepository bestellartRepository;
    private GerichtBestellungRepository gerichtBestellungRepository;
    private UmsatzRepository umsatzRepository;

    @Autowired
    public BestellungServiceImpl(BestellungRepository bestellungRepository, KundeRepository kundeRepository, MandantRepository mandantRepository, BestellstatusRepository bestellstatusRepository, GerichtRepository gerichtRepository, BestellartRepository bestellartRepository, GerichtBestellungRepository gerichtBestellungRepository, UmsatzRepository umsatzRepository) {
        this.bestellungRepository = bestellungRepository;
        this.kundeRepository = kundeRepository;
        this.mandantRepository = mandantRepository;
        this.bestellstatusRepository = bestellstatusRepository;
        this.gerichtRepository = gerichtRepository;
        this.bestellartRepository = bestellartRepository;
        this.gerichtBestellungRepository = gerichtBestellungRepository;
        this.umsatzRepository = umsatzRepository;
    }

    @Override
    public List<Bestellung> alleBestellungen(Long mandantId, Bestellstatus bestellstatus) {
        return bestellungRepository.getBestellungByMandant_IdAndBestellstatus(mandantId, bestellstatus);
    }

    @Override
    public void bestellungAufgeben(EinkaufswagenDto einkaufswagenDto, UserPrincipal userPrincipal, BezahlDto bezahlDto) {
        Mandant mandant = mandantRepository.getById(einkaufswagenDto.getMandantId());
        BestellartEnum bestellartEnum = BestellartEnum.valueOf(einkaufswagenDto.getBestellArt());
        int dauer = bestellartEnum == BestellartEnum.LIEFERUNG ? mandant.getDurchschnittsLieferZeit() : mandant.getDurchschnittsAbholZeit();

        Bestellung bestellung = new Bestellung().builder().id(null)
                .dauer(dauer).bestelldatum(Timestamp.valueOf(LocalDateTime.now()))
                .gesamtpreis(bezahlDto.getPreis()).trinkgeld(0.0)
                .bestellart(bestellartRepository.getBestellartByBestellart(bestellartEnum))
                .kunde(kundeRepository.findByEmail(userPrincipal.getUsername()))
                .mandant(mandant)
                .bestellstatus(bestellstatusRepository.getBestellstatusByBestellstatus(BestellstatusEnum.IN_ZUBEREITUNG)).build();

        bestellungRepository.save(bestellung);

        for (EinkaufswagenDatailDto einkaufswagenDatailDto:einkaufswagenDto.getEinkaufswagenDatails()) {
            //einkaufswagenDatailDto.getGerichtID()
            for (int i=0; i<einkaufswagenDatailDto.getAnzahl(); i++){
                GerichtBestellung gerichtBestellung = new GerichtBestellung(null, einkaufswagenDatailDto.getAnmerkung(), gerichtRepository.getById(einkaufswagenDatailDto.getGerichtID()), bestellung);
                gerichtBestellungRepository.save(gerichtBestellung);
            }

        }

    }

    @Override
    public EinkaufswagenDto deserializeEinkaufswagen(String einkaufswagen) {

        EinkaufswagenDatailDto einkaufswagenDatailDto = new EinkaufswagenDatailDto().builder().gerichtID(1L).anmerkung("Extra Tomaten").anzahl(1).build();
        EinkaufswagenDatailDto einkaufswagenDatailDto2 = new EinkaufswagenDatailDto().builder().gerichtID(2L).anmerkung("Extra Tomaten").anzahl(3).build();

        List<EinkaufswagenDatailDto> einkaufswagenDatailDtoList = new ArrayList<>();
        einkaufswagenDatailDtoList.add(einkaufswagenDatailDto);
        einkaufswagenDatailDtoList.add(einkaufswagenDatailDto2);


        EinkaufswagenDto einkaufswagenDto = new EinkaufswagenDto().builder().mandantId(1L).einkaufswagenDatails(einkaufswagenDatailDtoList).bestellArt("LIEFERUNG").build();

        return einkaufswagenDto;
    }



    @Override
    public BezahlDto getBezahlDto(EinkaufswagenDto einkaufswagenDto) {
        Mandant mandant = mandantRepository.getById(einkaufswagenDto.getMandantId());

        double preis;
        preis = einkaufswagenDto.getBestellArt().equals(BestellartEnum.LIEFERUNG.toString()) ? mandant.getLieferkosten() : 0.0;
        List<EinkaufswagenDatailDto> einkaufswagenDatailDtoList = einkaufswagenDto.getEinkaufswagenDatails();
        for (EinkaufswagenDatailDto einkaufswagenDatailDto : einkaufswagenDatailDtoList) {
            preis += (gerichtRepository.getById(einkaufswagenDatailDto.getGerichtID()).getPreis()) * einkaufswagenDatailDto.getAnzahl();
        }
        preis = preis<mandant.getMindestbestellwert() ? mandant.getMindestbestellwert() : preis;
        preis = new BigDecimal(preis).setScale(2, RoundingMode.HALF_UP).doubleValue();

        long lastBestellnummer = bestellungRepository.findTopByOrderByIdDesc().isPresent() ? bestellungRepository.findTopByOrderByIdDesc().get().getId() : 0L;
        String bestellnummer =  String.valueOf(lastBestellnummer+1L) + String.valueOf(mandant.getId()) + "-" + UUID.randomUUID();
        String mandantMessage = "Bestellung 001 von Lieferrex";


        System.out.println("Preis:" + preis);
        System.out.println("bestellnummer:" + bestellnummer);
        System.out.println("email:" + mandant.getEmail());



        BezahlDto bezahlDto = new BezahlDto().builder()
                .preis(preis)
                .kundenNachricht("Vielen dank für deine Bestellung bei Lieferrex!   Deine Bestellnummer: " + bestellnummer)
                .mandantEmail(mandant.getEmail())
                .mandantNachricht("Eingehende Bestellung bei Lieferrex!   Bestellnummer: " + bestellnummer)
                .mandantName(mandant.getFirmenname())
                .bestellNr(bestellnummer).build();

        return bezahlDto;
    }

    @Override
    public List<BestellDto> getBestellDto(String kundenEmail) {
        Kunde kunde = kundeRepository.findByEmail(kundenEmail);
        List<Bestellung> bestellungList = bestellungRepository.getBestellungByKunde(kunde);

        List<BestellDto> bestellDtoList = new ArrayList<>();

        for (Bestellung bestellung: bestellungList) {

            HashMap<String, Integer> gerichtNameAnzahl = new HashMap<>();

            for (GerichtBestellung gericht : bestellung.getGerichteBestellungen()) {

                    if(!gerichtNameAnzahl.containsKey(gericht.getGericht().getName())){
                        gerichtNameAnzahl.put(gericht.getGericht().getName(),1);
                    } else{
                        gerichtNameAnzahl.put(gericht.getGericht().getName(),gerichtNameAnzahl.get(gericht.getGericht().getName())+1);
                    }


            }

            LocalDate localDate = bestellung.getBestelldatum().toLocalDateTime().toLocalDate();


            BestellDto bestellDto = new BestellDto().builder()
                    .bestellId(bestellung.getId())
                    .bestellArt(String.valueOf(bestellung.getBestellart().getBestellart()))
                    .datum(localDate.getDayOfMonth() + "." + localDate.getMonthValue() + "." + localDate.getYear())
                    .preis(bestellung.getGesamtpreis())
                    .status(String.valueOf(bestellung.getBestellstatus().getBestellstatus()))
                    .restaurantName(bestellung.getMandant().getFirmenname())
                    .strasseHausnummer(bestellung.getMandant().getStrasse() + " " + bestellung.getMandant().getHausnummer())
                    .gerichtNameAnzahl(gerichtNameAnzahl)
                    .rating(bestellung.getBewertung())
                    .build();

            bestellDtoList.add(bestellDto);
        }


        return bestellDtoList;
    }

    @Override
    public List<Bestellung> alleBestellungenByMandant(long mandantid) {
        return bestellungRepository.getBestellungByMandant_Id(mandantid);
    }

    @Override
    public boolean makeRating(UserPrincipal principal, long bestellId, int rating) {
        if (!(rating == 1 || rating == 2 || rating == 3 || rating == 4 || rating == 5)){
            return false;
        }

        Optional<Bestellung> optionalBestellung= bestellungRepository.findBestellungById(bestellId);
        if (!optionalBestellung.isPresent()){
            System.out.println("bestellung not present");
            return false;
        }

        Kunde kunde = kundeRepository.findByEmail(principal.getUsername());
        if (optionalBestellung.get().getKunde().getId() != kunde.getId()){
            System.out.println("wrong user");

            return false;
        }


        optionalBestellung.get().setBewertung(rating);
        System.out.println(optionalBestellung.get().getBewertung());
        bestellungRepository.save(optionalBestellung.get());

        return true;
    }


    @Override
    public Bestellung bestellungByIdAnzeigen(long bestellid) {
        return bestellungRepository.getBestellungById(bestellid);
    }

    @Override
    public Bestellstatus bestellstatusAnzeigen(BestellstatusEnum bestellstatusEnum) {
        return bestellstatusRepository.getBestellstatusByBestellstatus(bestellstatusEnum);
    }

    @Override
    public void save(Bestellung bestellung) {
        bestellungRepository.save(bestellung);
    }




}
