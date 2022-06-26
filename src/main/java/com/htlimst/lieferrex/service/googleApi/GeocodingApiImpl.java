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
            results = com.google.maps.GeocodingApi.geocode(context,adresse).components(ComponentFilter.country("Österreich")).language("de").await();
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

        System.out.println(placeId);

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
        GeoPosition geoPosition = new GeoPosition();


        System.out.println(adresse);

        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = geocoede(adresse);
        } catch (AdresseNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String geocodingResults = gson.toJson(results.length);
        System.out.println(geocodingResults);
        if (results.length == 0){
            throw new AdresseNotFoundException();
        }

        for (int i = 0; i < Integer.parseInt(geocodingResults); i++){
            System.out.println(gson.toJson(results[i]));
        }

        System.out.println(gson.toJson(results[0].placeId));

        if (gson.toJson(results[0].placeId).equals("\"ChIJfyqdJZsHbUcRr8Hk3XvUEhA\"")){

            System.out.println("Adresse nicht vorhanden");
            throw new AdresseNotFoundException();
        }


        //if "locationType": "APPROXIMATE"
        //then reverse lookup latlng mit postalcode als respnse
        if (!gson.toJson(results[0].geometry.locationType).equals("\"APPROXIMATE\"")){
            int length = Integer.parseInt(gson.toJson(results[0].addressComponents.length));
            String tmp = gson.toJson(results[0].addressComponents[length-1].longName);
            tmp = tmp.substring(1);
            tmp = tmp.substring(0,tmp.length()-1);
            return tmp;
        }else {
            double lat = Double.parseDouble(gson.toJson(results[0].geometry.location.lat));
            double lng = Double.parseDouble(gson.toJson(results[0].geometry.location.lng));
            LatLng latLng = new LatLng( lat, lng);
            String ort = reverseGeocodingApi.getPlz(latLng);
            return ort;
        }

    }

    @Override
    public String findAdresseByPlz(String plz) {
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = geocoede(plz);
        } catch (AdresseNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String geocodingResults = gson.toJson(results.length);
        System.out.println(geocodingResults);

        for (int i = 0; i < Integer.parseInt(geocodingResults); i++){
            System.out.println(gson.toJson(results[i]));
        }


        return null;
    }


}
