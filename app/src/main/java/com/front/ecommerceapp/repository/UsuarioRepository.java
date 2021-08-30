package com.front.ecommerceapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.front.ecommerceapp.api.ConfigApi;
import com.front.ecommerceapp.api.UsuarioApi;
import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private static UsuarioRepository repository;
    private final UsuarioApi api;

    //constructor para traer al UsuarioApi
    public UsuarioRepository() {
        this.api = ConfigApi.getUsuarioApi();
    }

    public static UsuarioRepository getInstance(){
        if(repository==null){
            repository=new UsuarioRepository();
        }
        return repository;
    }

    //para traer errores el genericResponse
    public LiveData<GenericResponse<Usuario>>login(String email, String constrasenia){
        final MutableLiveData<GenericResponse<Usuario>>mld=new MutableLiveData<>();
        this.api.login(email, constrasenia).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());

            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesión");
                t.printStackTrace();
            }
        });
        return mld;
    }
    //Método para guardar usuario
    public LiveData<GenericResponse<Usuario>>save(Usuario u){
        final MutableLiveData<GenericResponse<Usuario>>mld=new MutableLiveData<>();
        this.api.save(u).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error : "+ t.getMessage());
                t.printStackTrace();
            }
        });
        //creado el método
        return mld;
    }
}
