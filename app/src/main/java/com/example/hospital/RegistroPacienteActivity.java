package com.example.hospital;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.controlador.ConsultaController;
import com.example.hospital.controlador.PacienteController;
import com.example.hospital.modelo.Consulta;
import com.example.hospital.modelo.Paciente;
import com.google.android.material.textfield.TextInputEditText;

public class RegistroPacienteActivity extends AppCompatActivity {

    private TextInputEditText etNombre;
    private TextInputEditText etApellido;
    private TextInputEditText etEdad;
    private Spinner spSexo;
    private TextInputEditText etIdDoctor;
    private TextInputEditText etFechaConsulta;
    private TextInputEditText etDiagnostico;
    private TextInputEditText etTratamiento;
    private TextInputEditText etHoraEntrada;
    private TextInputEditText etHoraSalida;
    private Button btnGuardar;
    private Button btnVolver;

    private PacienteController pacienteController;
    private ConsultaController consultaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        // Inicializar controladores
        pacienteController = new PacienteController(this);
        consultaController = new ConsultaController(this);

        // Vincular componentes
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEdad = findViewById(R.id.etEdad);
        spSexo = findViewById(R.id.spSexo);
        etIdDoctor = findViewById(R.id.etIdDoctor);
        etFechaConsulta = findViewById(R.id.etFechaConsulta);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        etTratamiento = findViewById(R.id.etTratamiento);
        etHoraEntrada = findViewById(R.id.etHoraEntrada);
        etHoraSalida = findViewById(R.id.etHoraSalida);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);

        // Poblar Spinner de sexo
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Masculino", "Femenino", "Otro"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adapter);

        // Listener para volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener para guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRegistro();
            }
        });
    }

    private void guardarRegistro() {
        // Obtener y limpiar datos
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();
        String sexo = spSexo.getSelectedItem().toString();
        String idDoctorStr = etIdDoctor.getText().toString().trim();
        String fechaConsulta = etFechaConsulta.getText().toString().trim();
        String diagnostico = etDiagnostico.getText().toString().trim();
        String tratamiento = etTratamiento.getText().toString().trim();
        String horaEntrada = etHoraEntrada.getText().toString().trim();
        String horaSalida = etHoraSalida.getText().toString().trim();

        // Validar campos obligatorios (todos los campos son requeridos)
        if (nombre.isEmpty() || apellido.isEmpty() || edadStr.isEmpty() ||
                idDoctorStr.isEmpty() || fechaConsulta.isEmpty() ||
                diagnostico.isEmpty() || tratamiento.isEmpty() ||
                horaEntrada.isEmpty() || horaSalida.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que edad y idDoctor sean números
        int edad;
        int idDoctor;
        try {
            edad = Integer.parseInt(edadStr);
            idDoctor = Integer.parseInt(idDoctorStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad e ID del Doctor deben ser números válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Insertar paciente
        Paciente paciente = new Paciente(0, nombre, apellido, edad, sexo);
        long idPacienteGenerado = pacienteController.insertarPaciente(paciente);

        if (idPacienteGenerado == -1) {
            Toast.makeText(this, "Error al registrar el paciente", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Insertar consulta vinculada al paciente
        Consulta consulta = new Consulta(
                0,
                (int) idPacienteGenerado,
                idDoctor,
                fechaConsulta,
                diagnostico,
                tratamiento,
                horaEntrada,
                horaSalida
        );
        long idConsultaGenerado = consultaController.insertarConsulta(consulta);

        if (idConsultaGenerado == -1) {
            Toast.makeText(this, "Paciente registrado pero error al guardar la consulta", Toast.LENGTH_SHORT).show();
            // Opcional: podrías eliminar el paciente insertado para mantener consistencia,
            // pero no se solicita en la tarea.
        } else {
            Toast.makeText(this, "Registro guardado correctamente", Toast.LENGTH_SHORT).show();
            // Opcional: cerrar la actividad o limpiar campos
            finish();
        }
    }
}
