package com.htlimst.lieferrex.service.googleApi;

import com.htlimst.lieferrex.dto.AdressDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlacesApi {
    public List<String> getPlaces(String adresse) throws AdresseNotFoundException;
    public List<AdressDto> getAdressen(String adresse) throws AdresseNotFoundException;


    }
