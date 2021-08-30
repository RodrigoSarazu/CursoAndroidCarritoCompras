package com.front.ecommerceapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.front.ecommerceapp.api.CategoriaApi;
import com.front.ecommerceapp.api.ConfigApi;
import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaRepository {
    private final CategoriaApi api;
    private static CategoriaRepository repository;

    public CategoriaRepository() {
        this.api= ConfigApi.getCategoriaApi();
    }
    //Obteniendo la instancia de la clase categoria repository
    public static CategoriaRepository getInstance(){
        if(repository==null){
            repository=new CategoriaRepository();
        }
        return repository;
    }

    //Llamando al listado de CategoriaApi
    public LiveData<GenericResponse<List<Categoria>>>listarCategoriasActivas(){
        final MutableLiveData<GenericResponse<List<Categoria>>> mld=new MutableLiveData<>();
        this.api.listarCategoriasActivas().enqueue(new Callback<GenericResponse<List<Categoria>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Categoria>>> call, Response<GenericResponse<List<Categoria>>> response) {
                mld.setValue(response.body());


            }

            @Override
            public void onFailure(Call<GenericResponse<List<Categoria>>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Error al obtener las categorias : "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;

    }

}
