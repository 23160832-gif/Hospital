package com.example.hospital.modelo;

public class Usuario {
    private int idUsuario;
    private String usuario;
    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(int idUsuario, String usuario, String password, String rol) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", usuario='" + usuario + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}