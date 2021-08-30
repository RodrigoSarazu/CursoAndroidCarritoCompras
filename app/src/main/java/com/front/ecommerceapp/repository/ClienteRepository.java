package com.front.ecommerceapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.front.ecommerceapp.api.ClienteApi;
import com.front.ecommerceapp.api.ConfigApi;
import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository {
    //trayendo clases
    private static ClienteRepository repository;
    private final ClienteApi api;

    public static  ClienteRepository getInstance(){
        if(repository==null){
            repository=new ClienteRepository();
        }
        return repository;
    }
    //trayendo al getClienteApi de config
    private ClienteRepository(){
        api= ConfigApi.getClienteApi();
    }
    //guardar datos del cliente
    public LiveData<GenericResponse<Cliente>>guardarCliente(Cliente c){
        final MutableLiveData<GenericResponse<Cliente>>mld=new MutableLiveData<>();
        this.api.guardarCliente(c).enqueue(new Callback<GenericResponse<Cliente>>() {
            @Override
            public void onResponse(Call<GenericResponse<Cliente>> call, Response<GenericResponse<Cliente>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Cliente>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error : "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}