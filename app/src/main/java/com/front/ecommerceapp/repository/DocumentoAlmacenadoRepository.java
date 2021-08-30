package com.front.ecommerceapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.front.ecommerceapp.api.ConfigApi;
import com.front.ecommerceapp.api.DocumentoAlmacenadoApi;
import com.front.ecommerceapp.entity.GenericResponse;
import com.front.ecommerceapp.entity.service.DocumentoAlmacenado;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentoAlmacenadoRepository {
    private static DocumentoAlmacenadoRepository repository;
    private final DocumentoAlmacenadoApi api;

    public DocumentoAlmacenadoRepository() {
        this.api = ConfigApi.getDocumentoAlmacenadoApi();
    }
    //con esto se va a poder crear los procedimientos de Call Retrofit
    public static DocumentoAlmacenadoRepository getInstance(){
        if(repository==null){
            repository=new DocumentoAlmacenadoRepository();
        }
        return repository;
    }
    public LiveData<GenericResponse<DocumentoAlmacenado>>saveFoto(MultipartBody.Part part, RequestBody requestBody){
        final MutableLiveData<GenericResponse<DocumentoAlmacenado>>mld=new MutableLiveData<>();
        this.api.save(part,requestBody).enqueue(new Callback<GenericResponse<DocumentoAlmacenado>>() {
            @Override
            public void onResponse(Call<GenericResponse<DocumentoAlmacenado>> call, Response<GenericResponse<DocumentoAlmacenado>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<DocumentoAlmacenado>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error : "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
