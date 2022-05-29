package com.htlimst.lieferrex.service.googleApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.model.GeoPosition;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GeocodingApi {

    private String apiKey = "AaSyDvURudmDr2bHk-KoAnaMogDiDuHs";
    private GeocodingResult[] results = new GeocodingResult[0];

    public GeoPosition getGeodaten(String strasse, String hausnummer) throws AdresseNotFoundException {

        GeoPosition geoPosition = new GeoPosition();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        String adresse = strasse + " " + hausnummer;
        System.out.println(adresse);

        try {
            results = com.google.maps.GeocodingApi.geocode(context,adresse).components(ComponentFilter.country("Österreich")).region("at").await();
        } catch (ApiException e) {
            e.printStackTrace();
            throw new AdresseNotFoundException();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new AdresseNotFoundException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AdresseNotFoundException();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String geocodingResults = gson.toJson(results.length);
        System.out.println(geocodingResults);

        for (int i = 0; i< Integer.parseInt(geocodingResults); i++){
            System.out.println(gson.toJson(results[i]));
            System.out.println("-------------------------------------------------------------");
        }
        context.shutdown();

        if (results.length == 0){
            System.out.println("User nicht vorhanden");
            throw new AdresseNotFoundException();
        }
        geoPosition.setGeoLat(Double.parseDouble(gson.toJson(results[0].geometry.location.lat)));
        geoPosition.setGeoLat(Double.parseDouble(gson.toJson(results[0].geometry.location.lng)));

        return geoPosition;
    }
}
