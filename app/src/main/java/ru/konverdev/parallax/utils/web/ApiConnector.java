package ru.konverdev.parallax.utils.web;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiConnector {
    private static ApiParallaxLink apiParallaxLink;
    private static ApiYandex apiYandex;

    private static Retrofit getApiConnector(String base_url){
        return new Retrofit.Builder().baseUrl(base_url)
                .addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build())).build();
    }

    public static ApiParallaxLink getParallaxLinkApi(){
        if (apiParallaxLink == null)
            apiParallaxLink = getApiConnector(WebAddress.PLX_LINK_BASE_URL).create(ApiParallaxLink.class);
        return apiParallaxLink;
    }

    public static ApiYandex getYandexApi() {
        if (apiYandex == null)
            apiYandex = getApiConnector(WebAddress.YANDEX_API_RASP_URL).create(ApiYandex.class);
        return apiYandex;
    }
}
