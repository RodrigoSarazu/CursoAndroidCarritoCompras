package com.front.ecommerceapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.front.ecommerceapp.R;
import com.front.ecommerceapp.entity.service.Usuario;
import com.front.ecommerceapp.utils.DateSerializer;
import com.front.ecommerceapp.utils.TimeSerializer;
import com.front.ecommerceapp.viewmodel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.sql.Time;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private EditText edtMail, edtPassword;
    private Button btnIniciarSesion;
    private UsuarioViewModel viewModel;
    private TextInputLayout txtInputUsuario, txtInputPassword;
    private TextView txtNuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViewModel();
        this.init();
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(this).get(UsuarioViewModel.class);
    }
    private void init(){
        edtMail = findViewById(R.id.edtMail);
        edtPassword = findViewById(R.id.edtPassword);
        txtInputUsuario = findViewById(R.id.txtInputUsuario);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtNuevoUsuario = findViewById(R.id.txtNuevoUsuario);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(v->{
            try{
                if(validar()){
                    viewModel.login(edtMail.getText().toString(),edtPassword.getText().toString()).observe(this,response->{
                        if(response.getRpta()==1){
                            //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            //llamando metodo al Toast personalizado trayendo al response
                            toastCorrecto(response.getMessage());
                            //Almacenar usuario en sesión para shared preferences
                            Usuario u = response.getBody();
                            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor=preferences.edit();
                            final Gson g=new GsonBuilder()
                                    .registerTypeAdapter(Date.class,new DateSerializer())
                                    .registerTypeAdapter(Time.class, new TimeSerializer())
                                    .create();

                            editor.putString("UsuarioJson",g.toJson(u,new TypeToken<Usuario>(){
                            }.getType()));
                            editor.apply();
                            edtMail.setText("");
                            edtPassword.setText("");
                            startActivity(new Intent(this,InicioActivity.class));
                        }else{
                            toastIncorrecto("Credenciales Inválidas");
                        }
                    });
                }else{
                    toastIncorrecto("Por favor, complete todos los campos");
                }
            }catch (Exception e){
                toastIncorrecto("Se ha producido un error  al intentar loguearse"+ e.getMessage());
            }
        });
        txtNuevoUsuario.setOnClickListener(v -> {
            //reemplazando con lambda
            Intent i=new Intent(this,RegistrarUsuarioActivity.class);
            startActivity(i);

            //Transición de animación
             overridePendingTransition(R.anim.left_in,R.anim.left_out);
        });
        edtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputUsuario.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              //cuando el usuario comienze a escribir se le quite el error
                txtInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    //esto es un metodo personalizado para el Toast
    public void toastCorrecto(String msg){
        LayoutInflater layoutInflater=getLayoutInflater();
        //aqui vamos a traer el custom toast
        View view =layoutInflater.inflate(R.layout.custom_toast_ok,(ViewGroup)findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje=view.findViewById(R.id.txtMensajeToast1);
        txtMensaje.setText(msg);

        Toast toast=new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();

    }
    public void toastIncorrecto(String msg) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast2);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


    public boolean validar(){
        boolean retorno=true;
        String usuario, password;
        usuario=edtMail.getText().toString();
        password=edtPassword.getText().toString();
        if(usuario.isEmpty()){
            txtInputUsuario.setError("Ingrese su usuario y/o correo electronico");
            retorno=false;
        }else{
            txtInputUsuario.setErrorEnabled(false);
        }if(password.isEmpty()){
            txtInputPassword.setError("Ingrese su contraseña");
            retorno=false;
        }else{
            txtInputPassword.setErrorEnabled(false);
        }
        return retorno;

    }

    @Override
    protected void onStart() {
        super.onStart();
        //llamar al sharedPreferences
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        String pref=preferences.getString("UsuarioJson","");
        if(!pref.equals("")){
            toastCorrecto("Se detecto una sesión activa, el login será omitido");
            this.startActivity(new Intent(this,InicioActivity.class));
            //agregando animación
            this.overridePendingTransition(R.anim.left_in,R.anim.left_out);
        }

    }

    @Override
    public void onBackPressed() {
        //mandando Un dialogo de Alerta
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("has oprimido el botón atrás")
                .setContentText("¿Quieres cerrar la aplicación?")
                .setCancelText("No, Cancelar!").setConfirmText("Sí, Cerrar")
                .showCancelButton(true).setCancelClickListener(sDialog -> {
            sDialog.dismissWithAnimation();
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Operación cancelada")
                    .setContentText("No saliste de la app")
                    .show();
        }).setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismissWithAnimation();
            System.exit(0);
        }).show();
    }
}