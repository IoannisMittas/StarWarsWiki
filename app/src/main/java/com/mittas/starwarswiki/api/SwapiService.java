package com.mittas.starwarswiki.api;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SwapiService {
    @GET("users/{user}")
    Call<User> getCharacter(@Path("user") String );

}
