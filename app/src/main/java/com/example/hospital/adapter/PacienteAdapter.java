package com.example.hospital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public PacienteAdapter(Context context, List<Paciente> listaPacientes, ConsultaController consultaController) {
        this.context = context;
        this.listaPacientes = listaPacientes;
        this.consultaController = consultaController;
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
    }

    @Override
    public int getItemCount() {
        return listaPacientes != null ? listaPacientes.size() : 0;
    }

    /**
     * Busca la primera consulta asociada al idPaciente.
     *
     * @param idPaciente ID del paciente
     * @return Objeto Consulta si existe, null en caso contrario
     */
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

    /**
     * Actualiza la lista de pacientes y notifica los cambios.
     *
     * @param nuevosPacientes Nueva lista de pacientes
     */
    public void setPacientes(List<Paciente> nuevosPacientes) {
        this.listaPacientes = nuevosPacientes;
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