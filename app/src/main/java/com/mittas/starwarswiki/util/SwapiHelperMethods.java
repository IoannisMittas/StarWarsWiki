package com.mittas.starwarswiki.util;

import android.support.annotation.NonNull;

public class SwapiHelperMethods {

    /**
     * Get id from url. This is possible because of how Swapi is built.
     * Example: From "http://swapi.co/api/vehicles/5/", we know that the vehicle's id = 5
     */
    public static int getIdFromUrl(@NonNull String url) {
        // url example: http://swapi.co/api/vehicles/5/"
        int beginIndex = url.length() - 2;
        int endIndex = url.length() - 1;
        return Integer.parseInt(url.substring(beginIndex, endIndex));
    }
}