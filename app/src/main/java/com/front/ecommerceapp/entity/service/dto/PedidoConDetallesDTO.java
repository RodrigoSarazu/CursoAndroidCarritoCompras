package com.front.ecommerceapp.entity.service.dto;

import com.front.ecommerceapp.entity.service.DetallePedido;
import com.front.ecommerceapp.entity.service.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoConDetallesDTO {
    private Pedido pedido;
    //cargando un List por el iterable
    private List<DetallePedido> detallePedido;

    public PedidoConDetallesDTO() {
        this.pedido = new Pedido();
        this.detallePedido = new ArrayList<>();
    }

    public PedidoConDetallesDTO(Pedido pedido, List<DetallePedido> detallePedido) {
        this.pedido = pedido;
        this.detallePedido = detallePedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }
}
