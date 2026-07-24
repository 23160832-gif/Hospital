package com.example.hospital.modelo;

public class Doctor {
    private int idDoctor;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String cedulaProfesional;
    private String telefono;

    public Doctor() {
    }

    public Doctor(int idDoctor, String nombre, String apellido, String especialidad, String cedulaProfesional, String telefono) {
        this.idDoctor = idDoctor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.cedulaProfesional = cedulaProfesional;
        this.telefono = telefono;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "idDoctor=" + idDoctor +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", cedulaProfesional='" + cedulaProfesional + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}