package com.htlimst.lieferrex.service.googleApi;

import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.model.GeoPosition;
import org.springframework.stereotype.Service;

@Service
public interface GeocodingApi {
    public GeoPosition getGeodaten(String land, String ort, String plz, String strasse, String hausnummer) throws AdresseNotFoundException ;
    public String findOrtByAdresse(String adresse);
    public String findPlzByAdresse(String adresse) throws AdresseNotFoundException;


}
