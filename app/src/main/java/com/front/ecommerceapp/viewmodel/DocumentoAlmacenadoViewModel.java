package com.front.ecommerceapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.DocumentoAlmacenado;
import com.front.ecommerceapp.repository.DocumentoAlmacenadoRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DocumentoAlmacenadoViewModel extends AndroidViewModel {

    private static DocumentoAlmacenadoRepository repository;

    public DocumentoAlmacenadoViewModel(@NonNull Application application) {
        super(application);
        this.repository=DocumentoAlmacenadoRepository.getInstance();
    }
    public LiveData<GenericResponse<DocumentoAlmacenado>>save(MultipartBody.Part part, RequestBody requestBody){
        return this.repository.saveFoto(part,requestBody);
    }
}
