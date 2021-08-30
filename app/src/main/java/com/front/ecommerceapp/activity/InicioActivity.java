package com.front.ecommerceapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.front.ecommerceapp.R;
import com.front.ecommerceapp.api.ConfigApi;
import com.front.ecommerceapp.databinding.ActivityInicioBinding;
import com.front.ecommerceapp.entity.service.Usuario;
import com.front.ecommerceapp.utils.DateSerializer;
import com.front.ecommerceapp.utils.TimeSerializer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Date;
import java.sql.Time;

import de.hdodenhof.circleimageview.CircleImageView;

public class InicioActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_mis_compras, R.id.nav_configuracion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }


    //para cerrar sesión
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cerrarSesion:
                this.logout();
                break;
            case R.id.bolsaCompras:
                this.mostrarBolsa();
                break;
        }
        return  super.onOptionsItemSelected(item);
    }
    //mostrar el carrito
    private void mostrarBolsa() {
        Intent i=new Intent(this,PlatillosCarritoActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        final Gson g=new GsonBuilder()
                .registerTypeAdapter(Date.class,new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson=sp.getString("UsuarioJson",null);
        if(usuarioJson!=null){
            final Usuario u= g.fromJson(usuarioJson,Usuario.class);
            final View vistaHeader=binding.navView.getHeaderView(0);
            //llamando a los textView
            final TextView tvNombre=vistaHeader.findViewById(R.id.tvNombre),
                    tvCorreo=vistaHeader.findViewById(R.id.tvCorreo);

            //CircleImageView para traer la imagen
            final CircleImageView imgFoto=vistaHeader.findViewById(R.id.imgFotoPerfil);
            tvNombre.setText(u.getCliente().getNombreCompleto());
            tvCorreo.setText(u.getEmail());

            //agregando la foto al CircleImageView
            String url= ConfigApi.baseUrlE+"/api/documento-almacenado/download/"+u.getCliente().getFoto().getFileName();
            final Picasso picasso=new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.image_not_found)
                    .into(imgFoto);
        }


    }

    //metodo cerrar sesion
    private void logout() {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=preferences.edit();
        //llamando key del usuario
        editor.remove("UsuarioJson");
        editor.apply();
        this.finish();
        //animación
        this.overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onBackPressed() {

    }
}