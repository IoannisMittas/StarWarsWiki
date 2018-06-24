package com.mittas.starwarswiki.util;

import android.support.annotation.NonNull;

public class SwapiHelperMethods {

    private static final String BASE_URL = "http://swapi.co/api/";
    private static final String URL_FILMS = BASE_URL + "films/";
    private static final String URL_VEHICLES = BASE_URL + "vehicles/";

    /**
     * Get id from url. This is possible because of how Swapi is built.
     * Example: From "http://swapi.co/api/vehicles/5", we know that the vehicle's id = 5
     */
    public static int getIdFromUrl(@NonNull String url) {
        if (url.contains(URL_FILMS)) {
            return Integer.parseInt(url.substring(URL_FILMS.length(), url.length() - 1));
        } else if (url.contains(URL_VEHICLES)) {
            return Integer.parseInt(url.substring(URL_VEHICLES.length(), url.length() - 1));
        } else {
            return 0;
        }
    }
}
