package com.front.ecommerceapp.activity.ui.compras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.front.ecommerceapp.R;
import com.front.ecommerceapp.adapter.MisComprasAdapter;
import com.front.ecommerceapp.communication.Communication;
import com.front.ecommerceapp.entity.service.Usuario;
import com.front.ecommerceapp.utils.DateSerializer;
import com.front.ecommerceapp.utils.TimeSerializer;
import com.front.ecommerceapp.viewmodel.PedidoViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

//implementando Communication para traer el método showDetails
public class MisComprasFragment extends Fragment implements Communication {

    private PedidoViewModel pedidoViewModel;
    private RecyclerView rcvPedidos;
    private MisComprasAdapter adapter;

    //private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mis_compras,container,false);
    }
    //fragment


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initViewModel();
        initAdapter();
        loadData();
    }

    private void init(View v){
        rcvPedidos=v.findViewById(R.id.rcvMisCompras);
    }

    private void initViewModel() {
        pedidoViewModel=new ViewModelProvider(this).get(PedidoViewModel.class);
    }

    private void initAdapter() {
        adapter=new MisComprasAdapter(new ArrayList<>(), this);
        rcvPedidos.setLayoutManager(new GridLayoutManager(getContext(),1));
        rcvPedidos.setAdapter(adapter);
    }

    private void loadData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        //llamamos una llame para guardar
        String usuarioJson = sp.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Usuario u = g.fromJson(usuarioJson, Usuario.class);
            this.pedidoViewModel.listarPedidosPorCliente(u.getCliente().getId()).observe(getViewLifecycleOwner(), response -> {
                adapter.updateItems(response.getBody());
            });
        }
    }
    //Esto va más para una animación
    @Override
    public void showDetails(Intent i) {
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.above_in,R.anim.above_out);


    }
}