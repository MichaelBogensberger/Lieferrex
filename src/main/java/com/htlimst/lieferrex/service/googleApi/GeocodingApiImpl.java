package com.htlimst.lieferrex.service.googleApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.htlimst.lieferrex.dto.GeoPositionDto;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import com.htlimst.lieferrex.model.GeoPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GeocodingApiImpl implements GeocodingApi{

    @Autowired
    ReverseGeocodingApi reverseGeocodingApi;

    @Value("${google.api.key}")
    String apiKey;


    public GeocodingResult[] geocoede(String adresse) throws AdresseNotFoundException {

        GeocodingResult[] results;
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        try {
            results = com.google.maps.GeocodingApi.geocode(context,adresse)
                    .components(ComponentFilter.country("Österreich")).language("de").await();
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

        context.shutdown();

        return results;
    }

    public GeoPositionDto getGeodaten(String placeId) throws AdresseNotFoundException {

        GeoPositionDto geoPosition = new GeoPositionDto();
        GeocodingResult[] results = new GeocodingResult[0];
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        try {
            results = com.google.maps.GeocodingApi.newRequest(context).place(placeId).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String geocodingResults = gson.toJson(results.length);
        System.out.println(geocodingResults);
        if (results.length == 0){
            System.out.println("Adresse nicht vorhanden");
            throw new AdresseNotFoundException();
        }

        geoPosition.setLat(Double.parseDouble(gson.toJson(results[0].geometry.location.lat)));
        geoPosition.setLng(Double.parseDouble(gson.toJson(results[0].geometry.location.lng)));
        geoPosition.setPlz(gson.toJson(results[0].addressComponents[6].shortName).replace("\"", ""));

        return geoPosition;
    }


    @Override
    public String findPlzByAdresse(String adresse) throws AdresseNotFoundException {

        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = geocoede(adresse);
        } catch (AdresseNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String geocodingResults = gson.toJson(results.length);
        if (results.length == 0){
            throw new AdresseNotFoundException();
        }

        System.out.println(gson.toJson(results[0].placeId));

        if (gson.toJson(results[0].placeId).equals("\"ChIJfyqdJZsHbUcRr8Hk3XvUEhA\"")){
            System.out.println("Adresse nicht vorhanden");
            throw new AdresseNotFoundException();
        }

        //Bei z.B. Wien gibt es mehrere PLZ, deshalb leifert keine PLZ
        //if "locationType": "APPROXIMATE"
        //then reverse lookup latlng mit postalcode als respnse
        if (!gson.toJson(results[0].geometry.locationType).equals("\"APPROXIMATE\"")){
            int length = Integer.parseInt(gson.toJson(results[0].addressComponents.length));
            String plz = gson.toJson(results[0].addressComponents[length-1].longName);
            plz = plz.substring(1);
            plz = plz.substring(0,plz.length()-1);
            return plz;
        }else {
            double lat = Double.parseDouble(gson.toJson(results[0].geometry.location.lat));
            double lng = Double.parseDouble(gson.toJson(results[0].geometry.location.lng));
            LatLng latLng = new LatLng( lat, lng);
            String plz = reverseGeocodingApi.getPlz(latLng);
            return plz;
        }

    }

}
