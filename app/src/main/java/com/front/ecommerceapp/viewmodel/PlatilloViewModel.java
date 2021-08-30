package com.front.ecommerceapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Platillo;
import com.front.ecommerceapp.repository.PlatilloRepository;

import java.util.List;

public class PlatilloViewModel extends AndroidViewModel {
    private final PlatilloRepository repository;

    //Esto es para llamar al Repositorio
    public PlatilloViewModel(@NonNull Application application) {
        super(application);
        repository=PlatilloRepository.getInstance();
    }

    public LiveData<GenericResponse<List<Platillo>>>listarPlatillosRecomendados(){
        return this.repository.listarPlatillosRecomendados();
    }

    public LiveData<GenericResponse<List<Platillo>>>listarPlatillosPorCategorias(int idC){
        return this.repository.listarPlatillosPorCategoria(idC);
    }


}
