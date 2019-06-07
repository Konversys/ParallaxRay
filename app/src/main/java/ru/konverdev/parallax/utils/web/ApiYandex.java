package ru.konverdev.parallax.utils.web;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.konverdev.parallax.model.yandex_api.Direction;
import ru.konverdev.parallax.model.yandex_api.Stations;

public interface ApiYandex {
    @GET(WebAddress.YANDEX_API_DIRECTIONS_MSK)
    Call<Direction> getDirectionsMsk(@Query("from") String from, @Query("to") String to, @Query("date") String date);

    @GET(WebAddress.YANDEX_API_DIRECTIONS_LOCAL)
    Call<Direction> getDirectionsLocal(@Query("from") String from, @Query("to") String to, @Query("date") String date);

    @GET(WebAddress.YANDEX_API_STATIONS)
    Call<Stations> getStations(@Query("uid") String uid, @Query("date") String date);
}
