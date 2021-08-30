package com.front.ecommerceapp.api;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    String base="api/categoria";
    //listado de categorias
    @GET(base)
    Call<GenericResponse<List<Categoria>>>listarCategoriasActivas();
}
