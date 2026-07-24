package com.example.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private Button btnWifi;
    private Button btnUbicacion;
    private Button btnRegistrarPaciente;
    private Button btnListaPacientes;
    private Button btnVerBaseDatos;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Vincular componentes
        btnWifi = findViewById(R.id.btnWifi);
        btnUbicacion = findViewById(R.id.btnUbicacion);
        btnRegistrarPaciente = findViewById(R.id.btnRegistrarPaciente);
        btnListaPacientes = findViewById(R.id.btnListaPacientes);
        btnVerBaseDatos = findViewById(R.id.btnVerBaseDatos);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Configurar listeners
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, WifiActivity.class));
            }
        });

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, UbicacionActivity.class));
            }
        });

        btnRegistrarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, RegistroPacienteActivity.class));
            }
        });

        btnListaPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ListaPacientesActivity.class));
            }
        });

        btnVerBaseDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, BaseDatosActivity.class));
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}