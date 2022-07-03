package com.htlimst.lieferrex.service.googleApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.PlaceAutocompleteType;
import com.google.maps.model.PlaceType;
import com.htlimst.lieferrex.dto.AdressDto;
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
    public List<String> getPlaces(String adresse) throws AdresseNotFoundException {

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

        if (geocodingResults.length() == 0) {
            throw new AdresseNotFoundException();
        }

        List<String> adressen = new ArrayList<String>();
        String tmp;

        for (int i = 0; i < Integer.parseInt(geocodingResults); i++) {
            tmp = gson.toJson(predictions[i].description);
            tmp = tmp.substring(1);
            tmp = tmp.substring(0, tmp.length() - 1);
            adressen.add(tmp);
        }

        return adressen;
    }

    @Override
    public List<AdressDto> getAdressen(String adresse) throws AdresseNotFoundException {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        AutocompletePrediction[] predictions = new AutocompletePrediction[0];
        try {
            predictions = com.google.maps.PlacesApi
                    .placeAutocomplete(context, adresse, new PlaceAutocompleteRequest.SessionToken("Lieferrex"))
                    .language("de")
                    .components(ComponentFilter.country("at"))
                    .types(PlaceAutocompleteType.ADDRESS)
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

        if (geocodingResults.length() == 0) {
            System.out.println("User nicht vorhanden");
            throw new AdresseNotFoundException();
        }

        for (int i = 0; i < Integer.parseInt(geocodingResults); i++) {
            System.out.println(gson.toJson(predictions[i]));
            System.out.println("-------------------------------------------------------------");
        }

        List<AdressDto> adressen = new ArrayList<AdressDto>();
        String tmp;


        for (int i = 0; i < Integer.parseInt(geocodingResults); i++) {
            AdressDto adressDto = new AdressDto();


            tmp = gson.toJson(predictions[i].description);
            tmp = tmp.substring(1);
            tmp = tmp.substring(0, tmp.length() - 1);
            adressDto.setAdresse(tmp);

            adressDto.setLand("Österreich");
            if (!gson.toJson(predictions[i].terms.length).equals("4")) {
                continue;
            }
            System.out.println(gson.toJson(predictions[i].terms.length));
            String ort = gson.toJson(predictions[i].terms[2].value).replace("\"", "");
            adressDto.setOrt(ort);

            String strasse = gson.toJson(predictions[i].terms[0].value).replace("\"", "");
            adressDto.setStrasse(strasse);

            String hausnummer = gson.toJson(predictions[i].terms[1].value).replace("\"", "");
            adressDto.setHausnummer(hausnummer);

            String placeId = gson.toJson(predictions[i].placeId).replace("\"", "");
            adressDto.setPlaceId(placeId);

            System.out.println(adressDto.toString());
            adressen.add(adressDto);


        }

        return adressen;
    }

}
