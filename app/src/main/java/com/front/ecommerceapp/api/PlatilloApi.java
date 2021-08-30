package com.front.ecommerceapp.api;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Platillo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlatilloApi {
    String base="api/platillo";

    //Llamando a los platillos recomendados
    @GET(base)
    Call<GenericResponse<List<Platillo>>>listarPlatillosRecomendados();

    //Llamando una lista por categoria
    @GET(base+"/{idC}")
    Call<GenericResponse<List<Platillo>>>listarPlatillosPorCategoria(@Path("idC") int idC);

}
