package com.front.ecommerceapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.Usuario;
import com.front.ecommerceapp.repository.UsuarioRepository;

import org.jetbrains.annotations.NotNull;

public class UsuarioViewModel extends AndroidViewModel {
    private final UsuarioRepository repository;
    public UsuarioViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository=UsuarioRepository.getInstance();
    }

    //método login
    public LiveData<GenericResponse<Usuario>>login(String email, String pass){
        return  this.repository.login(email, pass);
    }
    //método save para crear el usuario
    public LiveData<GenericResponse<Usuario>>save(Usuario u){
        return this.repository.save(u);
    }

}
