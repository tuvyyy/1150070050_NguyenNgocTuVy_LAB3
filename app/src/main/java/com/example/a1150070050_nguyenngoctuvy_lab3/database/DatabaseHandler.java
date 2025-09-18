package com.example.a1150070050_nguyenngoctuvy_lab3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1150070050_nguyenngoctuvy_lab3.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "clinicManager";
    private static final String TABLE_PATIENTS = "patients";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PATIENTS_TABLE = "CREATE TABLE " + TABLE_PATIENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT" + ")";
        db.execSQL(CREATE_PATIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        onCreate(db);
    }

    // ➕ Add
    public void addPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, patient.getName());
        values.put(KEY_PHONE, patient.getPhone());
        db.insert(TABLE_PATIENTS, null, values);
        db.close();
    }

    // 📋 Get all
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PATIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();
                patient.setId(cursor.getInt(0));
                patient.setName(cursor.getString(1));
                patient.setPhone(cursor.getString(2));
                list.add(patient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // ✏️ Update
    public int updatePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, patient.getName());
        values.put(KEY_PHONE, patient.getPhone());
        return db.update(TABLE_PATIENTS, values, KEY_ID + "=?",
                new String[]{String.valueOf(patient.getId())});
    }

    // ❌ Delete
    public void deletePatient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATIENTS, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 🔍 Get by ID
    public Patient getPatient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PATIENTS, new String[]{KEY_ID, KEY_NAME, KEY_PHONE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) cursor.moveToFirst();

        Patient patient = new Patient(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
        );
        cursor.close();
        return patient;
    }
}
