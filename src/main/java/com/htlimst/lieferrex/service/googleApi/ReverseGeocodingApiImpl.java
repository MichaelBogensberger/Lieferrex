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
    public String getPlz(LatLng latLng) throws AdresseNotFoundException {


        GeocodingResult[] results = new GeocodingResult[0];

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        String land = "Österreich";


        try {
            results = GeocodingApi.reverseGeocode(context, latLng).language("de").resultType(AddressType.POSTAL_CODE).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.shutdown();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String geocodingResults = gson.toJson(results.length);
        System.out.println(geocodingResults);

        for (int i = 0; i < Integer.parseInt(geocodingResults); i++) {
            System.out.println(gson.toJson(results[i]));
            System.out.println("-------------------------------------------------------------");
        }

        String tmp = gson.toJson(results[0].addressComponents[0].longName);
        tmp = tmp.substring(1);
        tmp = tmp.substring(0,tmp.length()-1);

        return tmp;
    }
}
