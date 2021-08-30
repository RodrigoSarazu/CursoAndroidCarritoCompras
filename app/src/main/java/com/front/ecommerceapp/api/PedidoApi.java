package com.front.ecommerceapp.api;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.dto.GenerarPedidoDTO;
import com.front.ecommerceapp.entity.service.dto.PedidoConDetallesDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoApi {
    String base="api/pedido";

    @GET(base + "/misPedidos/{idCli}")
    Call<GenericResponse<List<PedidoConDetallesDTO>>>listarPedidosPorCliente(@Path("idCli")int idCli);

    @POST(base)
    Call<GenericResponse<GenerarPedidoDTO>>guardarPedido(@Body GenerarPedidoDTO dto);
}
