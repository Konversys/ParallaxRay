package ru.konverdev.parallax.utils.web;

public class WebAddress {
    public static final String PLX_LINK_BASE_URL = "http://konverdev.ru/api/";
    public static final String PLX_LINK_DIRECTIONS_VALID = "directions/valid";
    public static final String PLX_LINK_DIRECTIONS_ALL = "directions/all";
    public static final String PLX_LINK_CHECKSUM = "directions/checksum";
    public static final String PLX_LINK_PRODUCTS = "products/all";
    public static final String PLX_LINK_INVENTORIES = "inventories/all";

    static final String YANDEX_API_RASP_URL = "https://api.rasp.yandex.net/v3.0/";
    static final String YANDEX_API_PARALLAX_KEY = "ac705998-ef1f-4c68-b387-c2b3243bdca3";

    static final String YANDEX_API_RASP_PARAM_TIMEZONE = "&result_timezone=Europe/Moscow";

    public static final String YANDEX_API_DIRECTIONS_MSK = "search/?apikey=" + WebAddress.YANDEX_API_PARALLAX_KEY + YANDEX_API_RASP_PARAM_TIMEZONE + "&format=json&transport_types=train&system=express&transfers=false";
    public static final String YANDEX_API_DIRECTIONS_LOCAL = "search/?apikey=" + WebAddress.YANDEX_API_PARALLAX_KEY + "&format=json&transport_types=train&system=express&transfers=false";
    public static final String YANDEX_API_STATIONS = "thread/?apikey=" + WebAddress.YANDEX_API_PARALLAX_KEY + "&format=json";
}
