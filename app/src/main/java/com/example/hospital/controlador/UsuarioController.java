package com.example.hospital.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hospital.database.ConexionSQLite;
import com.example.hospital.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioController {

    private ConexionSQLite conexionSQLite;

    public UsuarioController(Context context) {
        this.conexionSQLite = new ConexionSQLite(context);
    }

    public long insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        long id = -1;

        try {
            ContentValues values = new ContentValues();
            values.put("usuario", usuario.getUsuario());
            values.put("password", usuario.getPassword());
            values.put("rol", usuario.getRol());

            id = db.insert("USUARIO", null, values);
        } finally {
            db.close();
        }

        return id;
    }

    public Usuario validarUsuario(String usuario, String password) {
        SQLiteDatabase db = conexionSQLite.getReadableDatabase();
        Usuario usuarioEncontrado = null;
        Cursor cursor = null;

        try {
            String selection = "usuario = ? AND password = ?";
            String[] selectionArgs = {usuario, password};

            cursor = db.query("USUARIO", null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"));
                String usuarioDb = cursor.getString(cursor.getColumnIndexOrThrow("usuario"));
                String passwordDb = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"));

                usuarioEncontrado = new Usuario(idUsuario, usuarioDb, passwordDb, rol);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return usuarioEncontrado;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        SQLiteDatabase db = conexionSQLite.getReadableDatabase();
        List<Usuario> listaUsuarios = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query("USUARIO", null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"));
                    String usuario = cursor.getString(cursor.getColumnIndexOrThrow("usuario"));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    String rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"));

                    listaUsuarios.add(new Usuario(idUsuario, usuario, password, rol));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return listaUsuarios;
    }

    public int actualizarUsuario(Usuario usuario) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        int filasActualizadas = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("usuario", usuario.getUsuario());
            values.put("password", usuario.getPassword());
            values.put("rol", usuario.getRol());

            String whereClause = "idUsuario = ?";
            String[] whereArgs = {String.valueOf(usuario.getIdUsuario())};

            filasActualizadas = db.update("USUARIO", values, whereClause, whereArgs);
        } finally {
            db.close();
        }

        return filasActualizadas;
    }

    public int eliminarUsuario(int idUsuario) {
        SQLiteDatabase db = conexionSQLite.getWritableDatabase();
        int filasEliminadas = 0;

        try {
            String whereClause = "idUsuario = ?";
            String[] whereArgs = {String.valueOf(idUsuario)};

            filasEliminadas = db.delete("USUARIO", whereClause, whereArgs);
        } finally {
            db.close();
        }

        return filasEliminadas;
    }
}