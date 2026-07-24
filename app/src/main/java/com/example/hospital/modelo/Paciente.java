package com.example.hospital.modelo;

public class Paciente {
    private int idPaciente;
    private String nombre;
    private String apellido;
    private int edad;
    private String sexo;

    public Paciente() {
    }

    public Paciente(int idPaciente, String nombre, String apellido, int edad, String sexo) {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "idPaciente=" + idPaciente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}