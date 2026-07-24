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

import java.util.ArrayList;
import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder> {

    private Context context;
    private List<Paciente> listaPacientes;
    private List<Paciente> listaFiltrada;
    private ConsultaController consultaController;
    private OnPacienteSelectedListener listener;
    private int selectedPosition = -1;

    public interface OnPacienteSelectedListener {
        void onPacienteSelected(int idPaciente);
    }

    public PacienteAdapter(Context context, List<Paciente> listaPacientes,
                           ConsultaController consultaController,
                           OnPacienteSelectedListener listener) {
        this.context = context;
        this.listaPacientes = listaPacientes;
        this.listaFiltrada = new ArrayList<>(listaPacientes);
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
        Paciente paciente = listaFiltrada.get(position);

        holder.tvIdPaciente.setText("ID: #" + paciente.getIdPaciente());
        holder.tvNombreCompleto.setText(paciente.getNombre() + " " + paciente.getApellido());
        holder.tvEdadSexo.setText(paciente.getEdad() + " años | " + paciente.getSexo());

        Consulta consulta = obtenerConsultaPorPaciente(paciente.getIdPaciente());

        if (consulta != null) {
            holder.tvFechaConsulta.setText("Fecha: " + consulta.getFechaConsulta());
            holder.tvDiagnostico.setText("Diagnóstico: " + consulta.getDiagnostico());
        } else {
            holder.tvFechaConsulta.setText("Fecha: N/A");
            holder.tvDiagnostico.setText("Diagnóstico: Sin registro");
        }

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onPacienteSelected(paciente.getIdPaciente());
                }
                Toast.makeText(context, "Paciente seleccionado: " +
                                paciente.getNombre() + " " + paciente.getApellido(),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = -1;
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onPacienteSelected(-1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaFiltrada != null ? listaFiltrada.size() : 0;
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
        this.listaFiltrada = new ArrayList<>(nuevosPacientes);
        selectedPosition = -1;
        notifyDataSetChanged();
    }


    public void filtrar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            // Mostrar todos
            listaFiltrada = new ArrayList<>(listaPacientes);
        } else {
            String busqueda = texto.trim().toLowerCase();
            List<Paciente> filtrados = new ArrayList<>();
            for (Paciente paciente : listaPacientes) {
                String nombreCompleto = (paciente.getNombre() + " " + paciente.getApellido()).toLowerCase();
                // Buscar en nombre completo (nombre + apellido)
                if (nombreCompleto.contains(busqueda)) {
                    filtrados.add(paciente);
                }
            }
            listaFiltrada = filtrados;
        }
        selectedPosition = -1; // Limpiar selección al filtrar
        notifyDataSetChanged();

        if (listener != null) {
            listener.onPacienteSelected(-1);
        }
    }

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