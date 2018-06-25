package com.mittas.starwarswiki.api;

import com.mittas.starwarswiki.api.model.FilmsPage;
import com.mittas.starwarswiki.api.model.CharactersPage;
import com.mittas.starwarswiki.api.model.PlanetsPage;
import com.mittas.starwarswiki.api.model.VehiclesPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SwapiService {

    @GET("people/")
    Call<CharactersPage> getCharactersPage(@Query("page") int page);

    @GET("films/")
    Call<FilmsPage> getFilmsPage(@Query("page") int page);

    @GET("vehicles/")
    Call<VehiclesPage> getVehiclesPage(@Query("page") int page);

    @GET("planets/")
    Call<PlanetsPage> getPlanetsPage(@Query("page") int page);

}
