package com.example.hospital.modelo;

public class Consulta {
    private int idConsulta;
    private int idPaciente;
    private int idDoctor;
    private String fechaConsulta;
    private String diagnostico;
    private String tratamiento;
    private String horaEntrada;
    private String horaSalida;

    public Consulta() {
    }

    public Consulta(int idConsulta, int idPaciente, int idDoctor, String fechaConsulta, String diagnostico, String tratamiento, String horaEntrada, String horaSalida) {
        this.idConsulta = idConsulta;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.fechaConsulta = fechaConsulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "idConsulta=" + idConsulta +
                ", idPaciente=" + idPaciente +
                ", idDoctor=" + idDoctor +
                ", fechaConsulta='" + fechaConsulta + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", horaEntrada='" + horaEntrada + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                '}';
    }
}