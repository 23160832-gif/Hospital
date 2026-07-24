package com.example.hospital.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hospital.database.ConexionSQLite;
import com.example.hospital.modelo.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorController {

    private ConexionSQLite conexionSQLite;

    public DoctorController(Context context) {
        this.conexionSQLite = new ConexionSQLite(context);
    }

    public long insertarDoctor(Doctor doctor) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        long id = -1;

        try {
            ContentValues values = new ContentValues();
            values.put("nombre", doctor.getNombre());
            values.put("apellido", doctor.getApellido());
            values.put("especialidad", doctor.getEspecialidad());
            values.put("cedulaProfesional", doctor.getCedulaProfesional());
            values.put("telefono", doctor.getTelefono());

            id = db.insert("DOCTOR", null, values);
        } finally {
            db.close();
        }

        return id;
    }

    public Doctor obtenerDoctorPorId(int idDoctor) {
        SQLiteDatabase db = conexionSQLite.getReadableDatabase();
        Doctor doctor = null;
        Cursor cursor = null;

        try {
            String selection = "idDoctor = ?";
            String[] selectionArgs = {String.valueOf(idDoctor)};

            cursor = db.query("DOCTOR", null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                String especialidad = cursor.getString(cursor.getColumnIndexOrThrow("especialidad"));
                String cedulaProfesional = cursor.getString(cursor.getColumnIndexOrThrow("cedulaProfesional"));
                String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));

                doctor = new Doctor(idDoctor, nombre, apellido, especialidad, cedulaProfesional, telefono);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return doctor;
    }

    public List<Doctor> obtenerTodosLosDoctores() {
        SQLiteDatabase db = conexionSQLite.getReadableDatabase();
        List<Doctor> listaDoctores = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query("DOCTOR", null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idDoctor = cursor.getInt(cursor.getColumnIndexOrThrow("idDoctor"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                    String especialidad = cursor.getString(cursor.getColumnIndexOrThrow("especialidad"));
                    String cedulaProfesional = cursor.getString(cursor.getColumnIndexOrThrow("cedulaProfesional"));
                    String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));

                    listaDoctores.add(new Doctor(idDoctor, nombre, apellido, especialidad, cedulaProfesional, telefono));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return listaDoctores;
    }

    public int actualizarDoctor(Doctor doctor) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        int filasActualizadas = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("nombre", doctor.getNombre());
            values.put("apellido", doctor.getApellido());
            values.put("especialidad", doctor.getEspecialidad());
            values.put("cedulaProfesional", doctor.getCedulaProfesional());
            values.put("telefono", doctor.getTelefono());

            String whereClause = "idDoctor = ?";
            String[] whereArgs = {String.valueOf(doctor.getIdDoctor())};

            filasActualizadas = db.update("DOCTOR", values, whereClause, whereArgs);
        } finally {
            db.close();
        }

        return filasActualizadas;
    }

    public int eliminarDoctor(int idDoctor) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        int filasEliminadas = 0;

        try {
            String whereClause = "idDoctor = ?";
            String[] whereArgs = {String.valueOf(idDoctor)};

            filasEliminadas = db.delete("DOCTOR", whereClause, whereArgs);
        } finally {
            db.close();
        }

        return filasEliminadas;
    }
}