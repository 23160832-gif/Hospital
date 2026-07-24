package com.example.hospital.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hospital.database.ConexionSQLite;
import com.example.hospital.modelo.Paciente;

import java.util.ArrayList;
import java.util.List;

public class PacienteController {

    private ConexionSQLite conexionSQLite;

    public PacienteController(Context context) {
        this.conexionSQLite = new ConexionSQLite(context);
    }

    public long insertarPaciente(Paciente paciente) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        long id = -1;

        try {
            ContentValues values = new ContentValues();
            values.put("nombre", paciente.getNombre());
            values.put("apellido", paciente.getApellido());
            values.put("edad", paciente.getEdad());
            values.put("sexo", paciente.getSexo());

            id = db.insert("PACIENTE", null, values);
        } finally {
            db.close();
        }

        return id;
    }

    public Paciente obtenerPacientePorId(int idPaciente) {
        SQLiteDatabase db = conexionSQLite.getReadableDatabase();
        Paciente paciente = null;
        Cursor cursor = null;

        try {
            String selection = "idPaciente = ?";
            String[] selectionArgs = {String.valueOf(idPaciente)};

            cursor = db.query("PACIENTE", null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                int edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"));
                String sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"));

                paciente = new Paciente(idPaciente, nombre, apellido, edad, sexo);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return paciente;
    }

    public List<Paciente> obtenerTodosLosPacientes() {
        SQLiteDatabase db = conexionSQLite.getReadableDatabase();
        List<Paciente> listaPacientes = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query("PACIENTE", null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idPaciente = cursor.getInt(cursor.getColumnIndexOrThrow("idPaciente"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                    int edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"));
                    String sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"));

                    listaPacientes.add(new Paciente(idPaciente, nombre, apellido, edad, sexo));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return listaPacientes;
    }

    public int actualizarPaciente(Paciente paciente) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        int filasActualizadas = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("nombre", paciente.getNombre());
            values.put("apellido", paciente.getApellido());
            values.put("edad", paciente.getEdad());
            values.put("sexo", paciente.getSexo());

            String whereClause = "idPaciente = ?";
            String[] whereArgs = {String.valueOf(paciente.getIdPaciente())};

            filasActualizadas = db.update("PACIENTE", values, whereClause, whereArgs);
        } finally {
            db.close();
        }

        return filasActualizadas;
    }

    public int eliminarPaciente(int idPaciente) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        int filasEliminadas = 0;

        try {
            String whereClause = "idPaciente = ?";
            String[] whereArgs = {String.valueOf(idPaciente)};

            filasEliminadas = db.delete("PACIENTE", whereClause, whereArgs);
        } finally {
            db.close();
        }

        return filasEliminadas;
    }
}