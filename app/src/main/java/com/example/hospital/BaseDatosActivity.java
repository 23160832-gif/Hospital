package com.example.hospital;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.controlador.ConsultaController;
import com.example.hospital.controlador.PacienteController;
import com.example.hospital.modelo.Consulta;
import com.example.hospital.modelo.Paciente;

import java.util.List;

public class BaseDatosActivity extends AppCompatActivity {

    private Spinner spTablas;
    private Button btnCargarTabla;
    private TextView tvDatosRaw;
    private Button btnVolver;

    private PacienteController pacienteController;
    private ConsultaController consultaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_datos);

        // Vincular componentes
        spTablas = findViewById(R.id.spTablas);
        btnCargarTabla = findViewById(R.id.btnCargarTabla);
        tvDatosRaw = findViewById(R.id.tvDatosRaw);
        btnVolver = findViewById(R.id.btnVolver);

        // Inicializar controladores
        pacienteController = new PacienteController(this);
        consultaController = new ConsultaController(this);

        // Poblar Spinner con opciones
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Pacientes", "Consultas"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTablas.setAdapter(adapter);

        // Listener para volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener para cargar tabla seleccionada
        btnCargarTabla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tablaSeleccionada = spTablas.getSelectedItem().toString();
                if (tablaSeleccionada.equals("Pacientes")) {
                    mostrarPacientes();
                } else if (tablaSeleccionada.equals("Consultas")) {
                    mostrarConsultas();
                }
            }
        });
    }

    private void mostrarPacientes() {
        List<Paciente> lista = pacienteController.obtenerTodosLosPacientes();

        if (lista == null || lista.isEmpty()) {
            tvDatosRaw.setText("La tabla 'pacientes' no contiene registros.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Paciente paciente : lista) {
            sb.append("ID: ").append(paciente.getIdPaciente())
                    .append(" | Nombre: ").append(paciente.getNombre())
                    .append(" ").append(paciente.getApellido())
                    .append(" | Edad: ").append(paciente.getEdad())
                    .append(" | Sexo: ").append(paciente.getSexo())
                    .append("\n-----------------------------------\n");
        }

        tvDatosRaw.setText(sb.toString());
    }

    private void mostrarConsultas() {
        List<Consulta> lista = consultaController.obtenerTodasLasConsultas();

        if (lista == null || lista.isEmpty()) {
            tvDatosRaw.setText("La tabla 'consultas' no contiene registros.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Consulta consulta : lista) {
            sb.append("ID Consulta: ").append(consulta.getIdConsulta())
                    .append(" | ID Paciente: ").append(consulta.getIdPaciente())
                    .append(" | ID Doctor: ").append(consulta.getIdDoctor())
                    .append("\nFecha: ").append(consulta.getFechaConsulta())
                    .append(" | Horario: ").append(consulta.getHoraEntrada())
                    .append(" - ").append(consulta.getHoraSalida())
                    .append("\nDiagnóstico: ").append(consulta.getDiagnostico())
                    .append("\nTratamiento: ").append(consulta.getTratamiento())
                    .append("\n-----------------------------------\n");
        }

        tvDatosRaw.setText(sb.toString());
    }
}