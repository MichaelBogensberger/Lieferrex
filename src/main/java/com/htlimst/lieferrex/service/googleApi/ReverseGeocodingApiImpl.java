package com.htlimst.lieferrex.service.googleApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReverseGeocodingApiImpl implements ReverseGeocodingApi{

    @Value("${google.api.key}")
    private String apiKey;

    @Override
    public String getPlz(LatLng latLng){

        GeocodingResult[] results = new GeocodingResult[0];
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        try {
            results = GeocodingApi.reverseGeocode(context, latLng)
                    .language("de").resultType(AddressType.POSTAL_CODE).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.shutdown();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String plz = gson.toJson(results[0].addressComponents[0].longName);
        plz = plz.substring(1);
        plz = plz.substring(0,plz.length()-1);

        return plz;
    }
}
