package com.example.hospital.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hospital.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE USUARIO (" +
                    "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "usuario TEXT, " +
                    "password TEXT, " +
                    "rol TEXT)";

    private static final String CREATE_TABLE_DOCTOR =
            "CREATE TABLE DOCTOR (" +
                    "idDoctor INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "apellido TEXT, " +
                    "especialidad TEXT, " +
                    "cedulaProfesional TEXT, " +
                    "telefono TEXT)";

    private static final String CREATE_TABLE_PACIENTE =
            "CREATE TABLE PACIENTE (" +
                    "idPaciente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "apellido TEXT, " +
                    "edad INTEGER, " +
                    "sexo TEXT)";

    private static final String CREATE_TABLE_CONSULTA =
            "CREATE TABLE CONSULTA (" +
                    "idConsulta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idPaciente INTEGER, " +
                    "idDoctor INTEGER, " +
                    "fechaConsulta TEXT, " +
                    "diagnostico TEXT, " +
                    "tratamiento TEXT, " +
                    "horaEntrada TEXT, " +
                    "horaSalida TEXT)";

    public ConexionSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_DOCTOR);
        db.execSQL(CREATE_TABLE_PACIENTE);
        db.execSQL(CREATE_TABLE_CONSULTA);

        db.execSQL("INSERT INTO USUARIO (usuario, password, rol) VALUES ('admin', 'admin123', 'Administrador')");
        db.execSQL("INSERT INTO USUARIO (usuario, password, rol) VALUES ('doctor1', 'doc123', 'Médico')");
        db.execSQL("INSERT INTO USUARIO (usuario, password, rol) VALUES ('doctor2', 'doc456', 'Médico')");
        db.execSQL("INSERT INTO USUARIO (usuario, password, rol) VALUES ('recepcion', 'rec123', 'Recepción')");

        db.execSQL("INSERT INTO DOCTOR (nombre, apellido, especialidad, cedulaProfesional, telefono) VALUES " +
                "('Juan', 'Pérez', 'Cardiología', '12345678', '555-1001')");
        db.execSQL("INSERT INTO DOCTOR (nombre, apellido, especialidad, cedulaProfesional, telefono) VALUES " +
                "('María', 'González', 'Dermatología', '87654321', '555-1002')");
        db.execSQL("INSERT INTO DOCTOR (nombre, apellido, especialidad, cedulaProfesional, telefono) VALUES " +
                "('Carlos', 'Ramírez', 'Pediatría', '11223344', '555-1003')");
        db.execSQL("INSERT INTO DOCTOR (nombre, apellido, especialidad, cedulaProfesional, telefono) VALUES " +
                "('Ana', 'Martínez', 'Ginecología', '44332211', '555-1004')");

        db.execSQL("INSERT INTO PACIENTE (nombre, apellido, edad, sexo) VALUES " +
                "('Luis', 'Fernández', 45, 'Masculino')");
        db.execSQL("INSERT INTO PACIENTE (nombre, apellido, edad, sexo) VALUES " +
                "('Elena', 'García', 32, 'Femenino')");
        db.execSQL("INSERT INTO PACIENTE (nombre, apellido, edad, sexo) VALUES " +
                "('Ricardo', 'Mendoza', 28, 'Masculino')");
        db.execSQL("INSERT INTO PACIENTE (nombre, apellido, edad, sexo) VALUES " +
                "('Sofía', 'López', 55, 'Femenino')");

        db.execSQL("INSERT INTO CONSULTA (idPaciente, idDoctor, fechaConsulta, diagnostico, tratamiento, horaEntrada, horaSalida) VALUES " +
                "(1, 1, '2026-07-20', 'Hipertensión', 'Medicación y dieta baja en sal', '08:00', '08:30')");
        db.execSQL("INSERT INTO CONSULTA (idPaciente, idDoctor, fechaConsulta, diagnostico, tratamiento, horaEntrada, horaSalida) VALUES " +
                "(2, 2, '2026-07-20', 'Dermatitis atópica', 'Cremas hidratantes y antihistamínicos', '09:30', '10:00')");
        db.execSQL("INSERT INTO CONSULTA (idPaciente, idDoctor, fechaConsulta, diagnostico, tratamiento, horaEntrada, horaSalida) VALUES " +
                "(3, 3, '2026-07-20', 'Infección respiratoria', 'Antibióticos y reposo', '11:00', '11:30')");
        db.execSQL("INSERT INTO CONSULTA (idPaciente, idDoctor, fechaConsulta, diagnostico, tratamiento, horaEntrada, horaSalida) VALUES " +
                "(4, 4, '2026-07-20', 'Control de embarazo', 'Ultrasonido y suplementos', '12:00', '12:45')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USUARIO");
        db.execSQL("DROP TABLE IF EXISTS DOCTOR");
        db.execSQL("DROP TABLE IF EXISTS PACIENTE");
        db.execSQL("DROP TABLE IF EXISTS CONSULTA");
        onCreate(db);
    }
}