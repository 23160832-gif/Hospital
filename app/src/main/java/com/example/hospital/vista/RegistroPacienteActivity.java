package com.example.hospital.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hospital.R;

import com.example.hospital.controlador.ConsultaController;
import com.example.hospital.controlador.DoctorController;
import com.example.hospital.controlador.PacienteController;
import com.example.hospital.modelo.Consulta;
import com.example.hospital.modelo.Doctor;
import com.example.hospital.modelo.Paciente;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private DoctorController doctorController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        pacienteController = new PacienteController(this);
        consultaController = new ConsultaController(this);
        doctorController = new DoctorController(this);

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

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Masculino", "Femenino", "Otro"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adapter);

        establecerFechaYHoraActual();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRegistro();
            }
        });
    }

    private void establecerFechaYHoraActual() {
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaActual = sdfFecha.format(new Date());

        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String horaActual = sdfHora.format(new Date());

        etFechaConsulta.setText(fechaActual);
        etFechaConsulta.setFocusable(false);
        etFechaConsulta.setClickable(false);

        etHoraEntrada.setText(horaActual);
        etHoraEntrada.setFocusable(false);
        etHoraEntrada.setClickable(false);

        etHoraSalida.setText(horaActual);
        etHoraSalida.setFocusable(false);
        etHoraSalida.setClickable(false);
    }

    private boolean soloLetras(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
    }

    private void guardarRegistro() {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();
        String sexo = spSexo.getSelectedItem().toString();
        String idDoctorStr = etIdDoctor.getText().toString().trim();
        String diagnostico = etDiagnostico.getText().toString().trim();
        String tratamiento = etTratamiento.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || edadStr.isEmpty() ||
                idDoctorStr.isEmpty() || diagnostico.isEmpty() || tratamiento.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!soloLetras(nombre)) {
            Toast.makeText(this, "El nombre solo debe contener letras", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!soloLetras(apellido)) {
            Toast.makeText(this, "El apellido solo debe contener letras", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "La edad debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        int idDoctor;
        try {
            idDoctor = Integer.parseInt(idDoctorStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El ID del doctor debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Doctor doctor = doctorController.obtenerDoctorPorId(idDoctor);
        if (doctor == null) {
            Toast.makeText(this, "El ID del doctor no existe en la base de datos", Toast.LENGTH_SHORT).show();
            return;
        }

        String fechaConsulta = etFechaConsulta.getText().toString().trim();
        String horaEntrada = etHoraEntrada.getText().toString().trim();
        String horaSalida = etHoraSalida.getText().toString().trim();

        Paciente paciente = new Paciente(0, nombre, apellido, edad, sexo);
        long idPacienteGenerado = pacienteController.insertarPaciente(paciente);

        if (idPacienteGenerado == -1) {
            Toast.makeText(this, "Error al registrar el paciente", Toast.LENGTH_SHORT).show();
            return;
        }

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
        } else {
            Toast.makeText(this, "Registro guardado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}