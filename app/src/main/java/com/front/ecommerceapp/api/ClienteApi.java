package com.front.ecommerceapp.api;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteApi {
    String base="api/cliente";
    @POST(base)
    Call<GenericResponse<Cliente>>guardarCliente(@Body Cliente c);
}
