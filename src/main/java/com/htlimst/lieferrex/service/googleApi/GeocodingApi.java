package com.htlimst.lieferrex.service.googleApi;

import com.htlimst.lieferrex.dto.GeoPositionDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.model.GeoPosition;
import org.springframework.stereotype.Service;

@Service
public interface GeocodingApi {
    GeoPositionDto getGeodaten(String placeId) throws AdresseNotFoundException ;
    String findPlzByAdresse(String adresse) throws AdresseNotFoundException;
}
