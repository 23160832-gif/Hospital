package com.example.hospital.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.PacienteAdapter;
import com.example.hospital.controlador.ConsultaController;
import com.example.hospital.controlador.PacienteController;
import com.example.hospital.modelo.Paciente;

import java.util.List;

public class ListaPacientesActivity extends AppCompatActivity
        implements PacienteAdapter.OnPacienteSelectedListener {
//hola
    private RecyclerView rvPacientes;
    private TextView tvSinPacientes;
    private Button btnVolver;
    private Button btnEliminarPaciente;

    private PacienteController pacienteController;
    private ConsultaController consultaController;
    private PacienteAdapter adapter;

    private int idPacienteSeleccionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);

        // Vincular componentes
        rvPacientes = findViewById(R.id.rvPacientes);
        tvSinPacientes = findViewById(R.id.tvSinPacientes);
        btnVolver = findViewById(R.id.btnVolver);
        btnEliminarPaciente = findViewById(R.id.btnEliminarPaciente);

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

        // Configurar listener para eliminar
        btnEliminarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPacienteSeleccionado();
            }
        });

        // Cargar lista inicial
        cargarListaPacientes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarListaPacientes();
    }

    private void cargarListaPacientes() {
        List<Paciente> lista = pacienteController.obtenerTodosLosPacientes();

        if (lista == null || lista.isEmpty()) {
            tvSinPacientes.setVisibility(View.VISIBLE);
            rvPacientes.setVisibility(View.GONE);
            btnEliminarPaciente.setVisibility(View.GONE);
        } else {
            tvSinPacientes.setVisibility(View.GONE);
            rvPacientes.setVisibility(View.VISIBLE);
            btnEliminarPaciente.setVisibility(View.VISIBLE);

            if (adapter == null) {
                adapter = new PacienteAdapter(this, lista, consultaController, this);
                rvPacientes.setAdapter(adapter);
            } else {
                adapter.setPacientes(lista);
            }
        }
    }

    // Implementación de la interfaz OnPacienteSelectedListener
    @Override
    public void onPacienteSelected(int idPaciente) {
        this.idPacienteSeleccionado = idPaciente;
        if (idPaciente == -1) {
            Toast.makeText(this, "Paciente deseleccionado", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarPacienteSeleccionado() {
        if (idPacienteSeleccionado == -1) {
            Toast.makeText(this, "Mantén presionado un paciente para seleccionarlo",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Confirmar eliminación
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Eliminar Paciente")
                .setMessage("¿Estás seguro de que deseas eliminar este paciente?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    int filas = pacienteController.eliminarPaciente(idPacienteSeleccionado);
                    if (filas > 0) {
                        Toast.makeText(ListaPacientesActivity.this,
                                "Paciente eliminado correctamente",
                                Toast.LENGTH_SHORT).show();
                        idPacienteSeleccionado = -1;
                        cargarListaPacientes();
                    } else {
                        Toast.makeText(ListaPacientesActivity.this,
                                "Error al eliminar paciente",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}