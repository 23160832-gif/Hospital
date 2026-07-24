package com.example.hospital.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hospital.database.ConexionSQLite;
import com.example.hospital.modelo.Consulta;

import java.util.ArrayList;
import java.util.List;

public class ConsultaController {

    private ConexionSQLite conexionSQLite;

    public ConsultaController(Context context) {
        this.conexionSQLite = new ConexionSQLite(context);
    }

    public long insertarConsulta(Consulta consulta) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        long id = -1;

        try {
            ContentValues values = new ContentValues();
            values.put("idPaciente", consulta.getIdPaciente());
            values.put("idDoctor", consulta.getIdDoctor());
            values.put("fechaConsulta", consulta.getFechaConsulta());
            values.put("diagnostico", consulta.getDiagnostico());
            values.put("tratamiento", consulta.getTratamiento());
            values.put("horaEntrada", consulta.getHoraEntrada());
            values.put("horaSalida", consulta.getHoraSalida());

            id = db.insert("CONSULTA", null, values);
        } finally {
            db.close();
        }

        return id;
    }

    public Consulta obtenerConsultaPorId(int idConsulta) {
        SQLiteDatabase db = conexionSQLite.getReadableDatabase();
        Consulta consulta = null;
        Cursor cursor = null;

        try {
            String selection = "idConsulta = ?";
            String[] selectionArgs = {String.valueOf(idConsulta)};

            cursor = db.query("CONSULTA", null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idPaciente = cursor.getInt(cursor.getColumnIndexOrThrow("idPaciente"));
                int idDoctor = cursor.getInt(cursor.getColumnIndexOrThrow("idDoctor"));
                String fechaConsulta = cursor.getString(cursor.getColumnIndexOrThrow("fechaConsulta"));
                String diagnostico = cursor.getString(cursor.getColumnIndexOrThrow("diagnostico"));
                String tratamiento = cursor.getString(cursor.getColumnIndexOrThrow("tratamiento"));
                String horaEntrada = cursor.getString(cursor.getColumnIndexOrThrow("horaEntrada"));
                String horaSalida = cursor.getString(cursor.getColumnIndexOrThrow("horaSalida"));

                consulta = new Consulta(idConsulta, idPaciente, idDoctor, fechaConsulta, diagnostico, tratamiento, horaEntrada, horaSalida);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return consulta;
    }

    public List<Consulta> obtenerTodasLasConsultas() {
        SQLiteDatabase db = conexionSQLite.getReadableDatabase();
        List<Consulta> listaConsultas = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query("CONSULTA", null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idConsulta = cursor.getInt(cursor.getColumnIndexOrThrow("idConsulta"));
                    int idPaciente = cursor.getInt(cursor.getColumnIndexOrThrow("idPaciente"));
                    int idDoctor = cursor.getInt(cursor.getColumnIndexOrThrow("idDoctor"));
                    String fechaConsulta = cursor.getString(cursor.getColumnIndexOrThrow("fechaConsulta"));
                    String diagnostico = cursor.getString(cursor.getColumnIndexOrThrow("diagnostico"));
                    String tratamiento = cursor.getString(cursor.getColumnIndexOrThrow("tratamiento"));
                    String horaEntrada = cursor.getString(cursor.getColumnIndexOrThrow("horaEntrada"));
                    String horaSalida = cursor.getString(cursor.getColumnIndexOrThrow("horaSalida"));

                    listaConsultas.add(new Consulta(idConsulta, idPaciente, idDoctor, fechaConsulta, diagnostico, tratamiento, horaEntrada, horaSalida));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return listaConsultas;
    }

    public int actualizarConsulta(Consulta consulta) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        int filasActualizadas = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("idPaciente", consulta.getIdPaciente());
            values.put("idDoctor", consulta.getIdDoctor());
            values.put("fechaConsulta", consulta.getFechaConsulta());
            values.put("diagnostico", consulta.getDiagnostico());
            values.put("tratamiento", consulta.getTratamiento());
            values.put("horaEntrada", consulta.getHoraEntrada());
            values.put("horaSalida", consulta.getHoraSalida());

            String whereClause = "idConsulta = ?";
            String[] whereArgs = {String.valueOf(consulta.getIdConsulta())};

            filasActualizadas = db.update("CONSULTA", values, whereClause, whereArgs);
        } finally {
            db.close();
        }

        return filasActualizadas;
    }

    public int eliminarConsulta(int idConsulta) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        int filasEliminadas = 0;

        try {
            String whereClause = "idConsulta = ?";
            String[] whereArgs = {String.valueOf(idConsulta)};

            filasEliminadas = db.delete("CONSULTA", whereClause, whereArgs);
        } finally {
            db.close();
        }

        return filasEliminadas;
    }
}