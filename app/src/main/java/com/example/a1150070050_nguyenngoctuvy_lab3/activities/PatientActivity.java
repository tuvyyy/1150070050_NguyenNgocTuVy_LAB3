package com.example.a1150070050_nguyenngoctuvy_lab3.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1150070050_nguyenngoctuvy_lab3.R;
import com.example.a1150070050_nguyenngoctuvy_lab3.adapter.PatientAdapter;
import com.example.a1150070050_nguyenngoctuvy_lab3.database.DatabaseHandler;
import com.example.a1150070050_nguyenngoctuvy_lab3.model.Patient;

import java.util.List;

public class PatientActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private PatientAdapter adapter;
    private List<Patient> patientList;

    private EditText etName, etPhone;
    private Button btnAdd;
    private RecyclerView rvPatients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        // Ánh xạ view
        etName = findViewById(R.id.etPatientName);
        etPhone = findViewById(R.id.etPatientPhone);
        btnAdd = findViewById(R.id.btnAddPatient);
        rvPatients = findViewById(R.id.rvPatients);

        // Khởi tạo database
        db = new DatabaseHandler(this);

        // Lấy danh sách bệnh nhân
        patientList = db.getAllPatients();

        // Gắn adapter cho RecyclerView
        adapter = new PatientAdapter(this, patientList, this::deletePatient);
        rvPatients.setLayoutManager(new LinearLayoutManager(this));
        rvPatients.setAdapter(adapter);

        // Xử lý nút thêm bệnh nhân
        btnAdd.setOnClickListener(v -> addNewPatient());
    }

    private void addNewPatient() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (!name.isEmpty() && !phone.isEmpty()) {
            db.addPatient(new Patient(name, phone));
            refreshList();
            etName.setText("");
            etPhone.setText("");
        }
    }

    private void refreshList() {
        patientList.clear();
        patientList.addAll(db.getAllPatients());
        adapter.notifyDataSetChanged();
    }

    public void deletePatient(int id) {
        db.deletePatient(id);
        refreshList();
    }
}
