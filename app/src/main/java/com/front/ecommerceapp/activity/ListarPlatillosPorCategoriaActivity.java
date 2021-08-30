package com.front.ecommerceapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.front.ecommerceapp.R;
import com.front.ecommerceapp.adapter.PlatillosPorCategoriaAdapter;
import com.front.ecommerceapp.entity.service.Platillo;
import com.front.ecommerceapp.viewmodel.PlatilloViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListarPlatillosPorCategoriaActivity extends AppCompatActivity {

    private PlatilloViewModel platilloViewModel;
    private PlatillosPorCategoriaAdapter adapter;
    private List<Platillo>platillos=new ArrayList<>();
    private RecyclerView rcvPlatilloPorCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_platillos_por_categoria);
        init();
        initViewModel();
        initAdapter();
        loadData();
    }

    private void init(){
        Toolbar toolbar=this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v->{
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in,R.anim.rigth_out);
        });
    }

    private void initViewModel() {
        final ViewModelProvider vmp=new ViewModelProvider(this);
        this.platilloViewModel=vmp.get(PlatilloViewModel.class);
    }

    private void initAdapter() {
        adapter=new PlatillosPorCategoriaAdapter(platillos);
        rcvPlatilloPorCategoria=findViewById(R.id.rcvPlatillosPorCategoria);
        rcvPlatilloPorCategoria.setAdapter(adapter);
        rcvPlatilloPorCategoria.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        int idC=getIntent().getIntExtra("idC",0);//Recibimos el idCategoria
        platilloViewModel.listarPlatillosPorCategorias(idC).observe(this,response -> {
            adapter.updateItems(response.getBody());
        });

    }
}