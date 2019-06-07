package ru.konverdev.parallax.utils.web;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.konverdev.parallax.model.Direction;
import ru.konverdev.parallax.model.Product;

public interface ApiParallaxLink {
    @GET(WebAddress.PLX_LINK_DIRECTIONS_VALID)
    Call<List<Direction>> getValidDirections();

    @GET(WebAddress.PLX_LINK_DIRECTIONS_ALL)
    Call<List<Direction>> getAllDirections();

    @GET(WebAddress.PLX_LINK_CHECKSUM)
    Call<String> getChecksum();

    @GET(WebAddress.PLX_LINK_PRODUCTS)
    Call<List<Product>> getProducts();

    @GET(WebAddress.PLX_LINK_INVENTORIES)
    Call<List<Product>> getInventories();
}
