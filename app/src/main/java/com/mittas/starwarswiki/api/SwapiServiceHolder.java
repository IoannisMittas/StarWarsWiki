package com.mittas.starwarswiki.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwapiServiceHolder {
    private static SwapiServiceHolder INSTANCE;

    private SwapiService service;

    private static final String BASE_URL = "https://swapi.co/api/";

    private SwapiServiceHolder() {
        service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SwapiService.class);
    }

    public static SwapiServiceHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SwapiServiceHolder();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public SwapiService getSwapiService() {
        return service;
    }
}
