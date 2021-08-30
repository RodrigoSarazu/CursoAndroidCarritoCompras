package com.front.ecommerceapp.activity.ui.inicio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.front.ecommerceapp.R;
import com.front.ecommerceapp.adapter.CategoriaAdapter;
import com.front.ecommerceapp.adapter.PlatillosRecomendadosAdapter;
import com.front.ecommerceapp.adapter.SliderAdapter;
import com.front.ecommerceapp.communication.Communication;
import com.front.ecommerceapp.entity.SliderItem;
import com.front.ecommerceapp.entity.service.Platillo;
import com.front.ecommerceapp.viewmodel.CategoriaViewModel;
import com.front.ecommerceapp.viewmodel.PlatilloViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class InicioFragment extends Fragment implements Communication {

    //Esto es para traer el listado de categorias
    private CategoriaViewModel categoriaViewModel;
    private GridView gvCategorias;
    private CategoriaAdapter categoriaAdapter;

    //traer el listado de platillos recomendados
    private PlatilloViewModel platilloViewModel;
    private RecyclerView rcvPlatillosRecomendados;
    private PlatillosRecomendadosAdapter adapter;
    private List<Platillo>platillos=new ArrayList<>();


    //private FragmentHomeBinding binding;
    private SliderView svCarrusel;
    private SliderAdapter sliderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_inicio,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }


    private void init(View v) {
        svCarrusel=v.findViewById(R.id.svCarrusel);
        //Categoria
        ViewModelProvider vmp=new ViewModelProvider(this);
        categoriaViewModel=vmp.get(CategoriaViewModel.class);
        //llamando a la categoria img del gridView
        gvCategorias=v.findViewById(R.id.gvCategorias);

        //Platillos recomendados

        rcvPlatillosRecomendados=v.findViewById(R.id.rcvPlatillosRecomendados);
        rcvPlatillosRecomendados.setLayoutManager(new GridLayoutManager(getContext(),1));
        platilloViewModel=vmp.get(PlatilloViewModel.class);
    }

    //Instanciando todos los adaptadores
    private void initAdapter() {
        //Carrusel Imagenes
        sliderAdapter = new SliderAdapter(getContext());

        svCarrusel.setSliderAdapter(sliderAdapter);

        svCarrusel.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        svCarrusel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        svCarrusel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        svCarrusel.setIndicatorSelectedColor(Color.WHITE);
        svCarrusel.setIndicatorUnselectedColor(Color.GRAY);
        svCarrusel.setScrollTimeInSec(4); //set scroll delay in seconds :
        svCarrusel.startAutoCycle();

        //Categorias
        //Instanciar la categoriaAdapter
        categoriaAdapter=new CategoriaAdapter(getContext(),R.layout.item_categorias, new ArrayList<>());
        gvCategorias.setAdapter(categoriaAdapter);

        //Platillos
        adapter=new PlatillosRecomendadosAdapter(platillos, this);
        rcvPlatillosRecomendados.setAdapter(adapter);


    }
    private void loadData() {
        //Crear la lista

        List<SliderItem>lista=new ArrayList<>();
        lista.add(new SliderItem(R.drawable.platillos_tipicos,"Los mejores platillos"));
        lista.add(new SliderItem(R.drawable.postres_ricos, "Los Mejores Postres"));
        lista.add(new SliderItem(R.drawable.postres_muysabrosos, "Los Mejores Postres"));
        lista.add(new SliderItem(R.drawable.peru_postres, "Los Mejores Postres"));
        sliderAdapter.updateItem(lista);

        //Llamar a la clase para ver la vista
        categoriaViewModel.listarCategoriasActivas().observe(getViewLifecycleOwner(),response->{
            if(response.getRpta()==1){
                categoriaAdapter.clear();
                categoriaAdapter.addAll(response.getBody());
                categoriaAdapter.notifyDataSetChanged();
            }else{
                System.out.println("Error al obtener las categorias activas");
            }
        });
        platilloViewModel.listarPlatillosRecomendados().observe(getViewLifecycleOwner(),response->{
            adapter.updateItems(response.getBody());
        });
    }

    @Override
    public void showDetails(Intent i) {
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }
}