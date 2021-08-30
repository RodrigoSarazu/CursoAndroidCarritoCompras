package com.front.ecommerceapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.dto.GenerarPedidoDTO;
import com.front.ecommerceapp.entity.service.dto.PedidoConDetallesDTO;
import com.front.ecommerceapp.repository.PedidoRepository;

import java.util.List;

public class PedidoViewModel extends AndroidViewModel {

    private final PedidoRepository repository;

    public PedidoViewModel(@NonNull Application application) {
        super(application);
        this.repository = PedidoRepository.getInstance();
    }
    //llamando al método del repository
    public LiveData<GenericResponse<List<PedidoConDetallesDTO>>>listarPedidosPorCliente(int idCli){
        return this.repository.listarPedidosPorCliente(idCli);
    }
    public LiveData<GenericResponse<GenerarPedidoDTO>>guardarPedido(GenerarPedidoDTO dto){
        return this.repository.save(dto);
    }

}
