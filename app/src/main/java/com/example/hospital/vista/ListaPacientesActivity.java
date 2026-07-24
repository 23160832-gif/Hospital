package com.example.hospital.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.PacienteAdapter;
import com.example.hospital.controlador.ConsultaController;
import com.example.hospital.controlador.PacienteController;
import com.example.hospital.modelo.Paciente;

import java.util.List;

public class ListaPacientesActivity extends AppCompatActivity {

    private RecyclerView rvPacientes;
    private TextView tvSinPacientes;
    private Button btnVolver;

    private PacienteController pacienteController;
    private ConsultaController consultaController;
    private PacienteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);

        // Vincular componentes
        rvPacientes = findViewById(R.id.rvPacientes);
        tvSinPacientes = findViewById(R.id.tvSinPacientes);
        btnVolver = findViewById(R.id.btnVolver);

        // Inicializar controladores
        pacienteController = new PacienteController(this);
        consultaController = new ConsultaController(this);

        // Configurar RecyclerView
        rvPacientes.setLayoutManager(new LinearLayoutManager(this));

        // Configurar listener para volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Cargar lista inicial
        cargarListaPacientes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar lista al regresar a esta actividad
        cargarListaPacientes();
    }

    private void cargarListaPacientes() {
        List<Paciente> lista = pacienteController.obtenerTodosLosPacientes();

        if (lista == null || lista.isEmpty()) {
            // Mostrar vista vacía
            tvSinPacientes.setVisibility(View.VISIBLE);
            rvPacientes.setVisibility(View.GONE);
        } else {
            // Mostrar RecyclerView con datos
            tvSinPacientes.setVisibility(View.GONE);
            rvPacientes.setVisibility(View.VISIBLE);

            if (adapter == null) {
                adapter = new PacienteAdapter(this, lista, consultaController);
                rvPacientes.setAdapter(adapter);
            } else {
                // Actualizar datos del adapter sin recrearlo
                adapter.setPacientes(lista);
            }
        }
    }
}