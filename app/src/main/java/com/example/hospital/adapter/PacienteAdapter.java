package com.example.hospital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.controlador.ConsultaController;
import com.example.hospital.modelo.Consulta;
import com.example.hospital.modelo.Paciente;

import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder> {

    private Context context;
    private List<Paciente> listaPacientes;
    private ConsultaController consultaController;
    private OnPacienteSelectedListener listener;
    private int selectedPosition = -1; // Para marcar visualmente el seleccionado

    // Interface para comunicar selección a la Activity
    public interface OnPacienteSelectedListener {
        void onPacienteSelected(int idPaciente);
    }

    public PacienteAdapter(Context context, List<Paciente> listaPacientes,
                           ConsultaController consultaController,
                           OnPacienteSelectedListener listener) {
        this.context = context;
        this.listaPacientes = listaPacientes;
        this.consultaController = consultaController;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_paciente, parent, false);
        return new PacienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int position) {
        Paciente paciente = listaPacientes.get(position);

        // Datos básicos del paciente
        holder.tvIdPaciente.setText("ID: #" + paciente.getIdPaciente());
        holder.tvNombreCompleto.setText(paciente.getNombre() + " " + paciente.getApellido());
        holder.tvEdadSexo.setText(paciente.getEdad() + " años | " + paciente.getSexo());

        // Obtener consulta asociada al paciente
        Consulta consulta = obtenerConsultaPorPaciente(paciente.getIdPaciente());

        if (consulta != null) {
            holder.tvFechaConsulta.setText("Fecha: " + consulta.getFechaConsulta());
            holder.tvDiagnostico.setText("Diagnóstico: " + consulta.getDiagnostico());
        } else {
            holder.tvFechaConsulta.setText("Fecha: N/A");
            holder.tvDiagnostico.setText("Diagnóstico: Sin registro");
        }

        // Marcar visualmente el elemento seleccionado
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        // Click largo para seleccionar
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Actualizar posición seleccionada
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged(); // Refrescar la lista para mostrar selección

                // Notificar a la Activity
                if (listener != null) {
                    listener.onPacienteSelected(paciente.getIdPaciente());
                }

                // Mostrar un Toast para confirmar selección
                Toast.makeText(context, "Paciente seleccionado: " +
                                paciente.getNombre() + " " + paciente.getApellido(),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Click corto para limpiar selección
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deseleccionar
                selectedPosition = -1;
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onPacienteSelected(-1); // -1 indica deselección
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPacientes != null ? listaPacientes.size() : 0;
    }

    private Consulta obtenerConsultaPorPaciente(int idPaciente) {
        List<Consulta> todasLasConsultas = consultaController.obtenerTodasLasConsultas();
        if (todasLasConsultas != null) {
            for (Consulta consulta : todasLasConsultas) {
                if (consulta.getIdPaciente() == idPaciente) {
                    return consulta;
                }
            }
        }
        return null;
    }

    public void setPacientes(List<Paciente> nuevosPacientes) {
        this.listaPacientes = nuevosPacientes;
        selectedPosition = -1; // Resetear selección
        notifyDataSetChanged();
    }

    // ViewHolder interno
    public static class PacienteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdPaciente;
        private TextView tvNombreCompleto;
        private TextView tvEdadSexo;
        private TextView tvFechaConsulta;
        private TextView tvDiagnostico;

        public PacienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdPaciente = itemView.findViewById(R.id.tvIdPaciente);
            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);
            tvEdadSexo = itemView.findViewById(R.id.tvEdadSexo);
            tvFechaConsulta = itemView.findViewById(R.id.tvFechaConsulta);
            tvDiagnostico = itemView.findViewById(R.id.tvDiagnostico);
        }
    }
}