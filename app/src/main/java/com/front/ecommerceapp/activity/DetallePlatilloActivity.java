package com.front.ecommerceapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.front.ecommerceapp.R;
import com.front.ecommerceapp.api.ConfigApi;
import com.front.ecommerceapp.entity.service.DetallePedido;
import com.front.ecommerceapp.entity.service.Platillo;
import com.front.ecommerceapp.utils.Carrito;
import com.front.ecommerceapp.utils.DateSerializer;
import com.front.ecommerceapp.utils.TimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetallePlatilloActivity extends AppCompatActivity {

    private ImageView imgPlatilloDetalle;
    private Button btnAgregarCarrito,btnComprar;
    private TextView tvNamePlatilloDetalle,tvPrecioPlatilloDetalle,tvDescripcionPlatilloDetalle;
    final Gson g=new GsonBuilder()
            .registerTypeAdapter(Date.class,new DateSerializer())
            .registerTypeAdapter(Time.class, new TimeSerializer())
            .create();
    Platillo platillo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_platillo);
        init();
        loadData();
    }
    private void init(){
        //Botton para regresar
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        //
        //animación
        toolbar.setNavigationOnClickListener(v -> {//Reemplazo con lamba
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
        //
        //asignando los img,botones y txt
        this.imgPlatilloDetalle = findViewById(R.id.imgPlatilloDetalle);
        this.btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);
        this.btnComprar = findViewById(R.id.btnComprar);
        this.tvNamePlatilloDetalle = findViewById(R.id.tvNamePlatilloDetalle);
        this.tvPrecioPlatilloDetalle = findViewById(R.id.tvPrecioPlatilloDetalle);
        this.tvDescripcionPlatilloDetalle = findViewById(R.id.tvDescripcionPlatilloDetalle);
    }
    private void loadData() {
        //recibiendo el putExtra del adapter platillosrecomendadosadapter
        final String detalleString = this.getIntent().getStringExtra("detallePlatillo");
        if (detalleString != null) {
            platillo = g.fromJson(detalleString, Platillo.class);
            this.tvNamePlatilloDetalle.setText(platillo.getNombre());
            this.tvPrecioPlatilloDetalle.setText(String.format(Locale.ENGLISH, "S/%.2f", platillo.getPrecio()));
            this.tvDescripcionPlatilloDetalle.setText(platillo.getDescripcionPlatillo());
            String url = ConfigApi.baseUrlE + "/api/documento-almacenado/download/" + platillo.getFoto().getFileName();

            Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.image_not_found)
                    .into(this.imgPlatilloDetalle);
        } else {
            System.out.println("Error al obtener los detalles del platillo");
        }
        //Agregar platillos al carrito
        this.btnAgregarCarrito.setOnClickListener(v -> {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPlatillo(platillo);
            detallePedido.setCantidad(1);
            detallePedido.setPrecio(platillo.getPrecio());
            //Aquí en success retorna un mensaje de la clase Carrito
            successMessage(Carrito.agregarPlatillos(detallePedido));
        });
    }
    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }

}