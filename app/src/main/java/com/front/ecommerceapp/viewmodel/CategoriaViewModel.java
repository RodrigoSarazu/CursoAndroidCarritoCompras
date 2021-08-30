package com.front.ecommerceapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Categoria;
import com.front.ecommerceapp.repository.CategoriaRepository;
import com.front.ecommerceapp.repository.ClienteRepository;

import java.util.List;

public class CategoriaViewModel extends AndroidViewModel {

    private final CategoriaRepository repository;

    public CategoriaViewModel(@NonNull Application application) {
        super(application);
        this.repository= CategoriaRepository.getInstance();
    }
    public LiveData<GenericResponse<List<Categoria>>>listarCategoriasActivas(){
        return this.repository.listarCategoriasActivas();
    }
}
