package com.luisdiaz.controlpersonalizado2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by luis-diaz on 10/08/15.
 */

public class ControlLogin extends LinearLayout {

    EditText txtUsuario, txtPassword;
    Button btnLogin;
    TextView lblMensaje;

    private OnLoginListener listener;

    public ControlLogin(Context context){
        super(context);
        inicializar();
    }

    public ControlLogin(Context context, AttributeSet attrs){
        super(context,attrs);
        inicializar();
    }

    private void inicializar(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;

        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);

        li.inflate(R.layout.control_login,this,true);

        //Obtenemoslas referencias a los distintos control
        txtUsuario = (EditText)findViewById(R.id.TxtUsuario);
        txtPassword = (EditText)findViewById(R.id.TxtPassword);
        btnLogin = (Button)findViewById(R.id.BtnAceptar);
        lblMensaje = (TextView)findViewById(R.id.LblMensaje);

        //Asociamos los eventos necesarios
        asignarEventos();
    }

    public void setMensaje(String msj){
        lblMensaje.setText(msj);
    }

    public void setOnLoginListener(OnLoginListener l){
        listener = l;
    }

    private void asignarEventos(){
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLogin(txtUsuario.getText().toString(),txtPassword.getText().toString());
            }
        });
    }
}
