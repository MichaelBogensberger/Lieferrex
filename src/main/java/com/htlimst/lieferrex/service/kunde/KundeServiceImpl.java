package com.htlimst.lieferrex.service.kunde;

import com.htlimst.lieferrex.dto.AdressDto;
import com.htlimst.lieferrex.dto.GeoPositionDto;
import com.htlimst.lieferrex.dto.KundeRegistrationDto;
import com.htlimst.lieferrex.dto.PasswortAendernDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.model.GeoPosition;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.repository.KundeRepository;
import com.htlimst.lieferrex.repository.RolleRepository;
import com.htlimst.lieferrex.service.googleApi.GeocodingApi;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class KundeServiceImpl implements KundeService{

    private KundeRepository kundeRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RolleRepository rolleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private GeocodingApi geocodingApi;

    @Autowired
    public KundeServiceImpl(KundeRepository kundeRepository, BCryptPasswordEncoder passwordEncoder, RolleRepository rolleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, GeocodingApi geocodingApi) {
        this.kundeRepository = kundeRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolleRepository = rolleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.geocodingApi = geocodingApi;
    }








    @Override
    public boolean save(KundeRegistrationDto registrationDto) {
        Kunde kunde = new Kunde();
        GeoPositionDto geoPositionDto = new GeoPositionDto();
        try {
            geoPositionDto = geocodingApi.getGeodaten(registrationDto.getPlaceId());
        } catch (AdresseNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        kunde.setVorname(registrationDto.getVorname());
        kunde.setNachname(registrationDto.getNachname());
        kunde.setEmail(registrationDto.getEmail());
        kunde.setPasswort(passwordEncoder.encode(registrationDto.getPasswort()));
        kunde.setOrt(registrationDto.getOrt());
        kunde.setPlz(geoPositionDto.getPlz());
        kunde.setStrasse(registrationDto.getStrasse());
        kunde.setHausnummer(registrationDto.getHausnummer());
        kunde.setTelefonnummer(registrationDto.getTelefonnummer());
        kunde.setLand(registrationDto.getLand());
        kunde.setNewsletter(registrationDto.isNewsletter());

        kundeRepository.save(kunde);

        return true;
    }

    @Override
    public boolean passwortAendern(PasswortAendernDto passwortAendernDto, UserPrincipal principal) {

        Kunde kunde = kundeRepository.findByEmail(principal.getUsername());
        String passwort = kunde.getPasswort();

        if (!passwordEncoder.matches(passwortAendernDto.getAltesPasswort(), passwort)){
            return false;
        }

        kunde.setPasswort(bCryptPasswordEncoder.encode(passwortAendernDto.getPasswort()));
        kundeRepository.save(kunde);


        return true;
    }

    @Override
    public boolean adresseAendern(AdressDto adressDto, UserPrincipal principal) {
        GeoPositionDto geoPositionDto;

        try {
            geoPositionDto = geocodingApi.getGeodaten(adressDto.getPlaceId());
        } catch (AdresseNotFoundException e) {
            System.out.println("Geodaten nicht gefunden");
            return false;
        }

        Kunde kunde = kundeRepository.findByEmail(principal.getUsername());

        kunde.setLand(adressDto.getLand());
        kunde.setOrt(adressDto.getOrt());
        kunde.setStrasse(adressDto.getStrasse());
        kunde.setHausnummer(adressDto.getHausnummer().toUpperCase());
        kunde.setPlz(geoPositionDto.getPlz());

        kundeRepository.save(kunde);

        return true;
    }


}
