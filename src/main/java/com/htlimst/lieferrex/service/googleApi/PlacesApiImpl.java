package com.htlimst.lieferrex.service.googleApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.ComponentFilter;
import com.htlimst.lieferrex.exceptions.AdresseNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlacesApiImpl implements PlacesApi {

    @Value("${google.api.key}")
    String apiKey;

    @Override
    public List<String> getPlaces(String adresse) throws AdresseNotFoundException{

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        AutocompletePrediction[] predictions = new AutocompletePrediction[0];
        try {
            predictions = com.google.maps.PlacesApi.placeAutocomplete(context, adresse, new PlaceAutocompleteRequest.SessionToken("Lieferrex"))
                    .language("de")
                    .components(ComponentFilter.country("at"))
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.shutdown();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String geocodingResults = gson.toJson(predictions.length);

        if (geocodingResults.length() == 0){
            System.out.println("User nicht vorhanden");
            throw new AdresseNotFoundException();
        }

        List<String> adressen = new  ArrayList<String>();
        String tmp;
        for (int i = 0; i < Integer.parseInt(geocodingResults); i++) {
            tmp = gson.toJson(predictions[i].description);
            tmp = tmp.substring(1);
            tmp = tmp.substring(0,tmp.length()-1);
;           adressen.add(tmp);
        }

        return adressen;
    }
}
