package com.htlimst.lieferrex.service.googleApi;

import com.google.maps.model.LatLng;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReverseGeocodingApi {

    public String getPlz(LatLng latLng) throws AdresseNotFoundException;


}
