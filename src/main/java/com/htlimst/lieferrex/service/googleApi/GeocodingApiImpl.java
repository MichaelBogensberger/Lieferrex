package com.htlimst.lieferrex.service.googleApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
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
        GeocodingResult[] results = new GeocodingResult[0];

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        try {
            results = com.google.maps.GeocodingApi.geocode(context,adresse).components(ComponentFilter.country("Österreich")).region("at").language("de").await();
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

    public GeoPosition getGeodaten(String land, String ort, String plz, String strasse, String hausnummer) throws AdresseNotFoundException {

        GeoPosition geoPosition = new GeoPosition();

        String adresse = strasse + " " + hausnummer;
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
            System.out.println("Adresse nicht vorhanden");
            throw new AdresseNotFoundException();
        }



        try {
            String foundLand = gson.toJson(results[0].addressComponents[5].longName);
            System.out.println(foundLand);

            String foundStadtInLong = gson.toJson(results[0].addressComponents[2].longName);
            System.out.println(foundStadtInLong);
            String foundStadtInShort = gson.toJson(results[0].addressComponents[2].shortName);
            System.out.println(foundStadtInShort);

            String foundPlz =  gson.toJson(results[0].addressComponents[6].longName);
            System.out.println(foundPlz);
            String foundHausnummer = gson.toJson(results[0].addressComponents[0].longName);
            System.out.println(foundHausnummer);
            String foundStrasse = gson.toJson(results[0].addressComponents[1].longName);
            System.out.println(foundStrasse);

            boolean equals;
            equals = foundPlz.equals("\"" + plz + "\"");
            equals = equals && foundHausnummer.equals("\"" + hausnummer + "\"");
            equals = equals && foundStrasse.equals("\"" + strasse + "\"");

            if(!equals){
                throw new AdresseNotFoundException();
            }
        }catch (IndexOutOfBoundsException indexOutOfBoundsException){
            throw new AdresseNotFoundException();
        }

        geoPosition.setGeoLat(Double.parseDouble(gson.toJson(results[0].geometry.location.lat)));
        geoPosition.setGeoLng(Double.parseDouble(gson.toJson(results[0].geometry.location.lng)));

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
