package com.front.ecommerceapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.front.ecommerceapp.api.ConfigApi;
import com.front.ecommerceapp.api.PlatilloApi;
import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Platillo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatilloRepository {

    private final PlatilloApi api;
    private static PlatilloRepository repository;


    public PlatilloRepository() {
        this.api= ConfigApi.getPlatilloApi();
    }
    //generar una instancia del repositorio
    public static PlatilloRepository getInstance(){
        if(repository==null){
            repository=new PlatilloRepository();
        }
        return repository;
    }
    //Metodo para listar los platillos recomendados
    public LiveData<GenericResponse<List<Platillo>>>listarPlatillosRecomendados(){
        final MutableLiveData<GenericResponse<List<Platillo>>>mld=new MutableLiveData<>();
        this.api.listarPlatillosRecomendados().enqueue(new Callback<GenericResponse<List<Platillo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Platillo>>> call, Response<GenericResponse<List<Platillo>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Platillo>>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }

    //Metodo para listar los platillos por categoria
    public LiveData<GenericResponse<List<Platillo>>>listarPlatillosPorCategoria(int idC){
        final MutableLiveData<GenericResponse<List<Platillo>>>mld=new MutableLiveData<>();
        this.api.listarPlatillosPorCategoria(idC).enqueue(new Callback<GenericResponse<List<Platillo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Platillo>>> call, Response<GenericResponse<List<Platillo>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Platillo>>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }

}