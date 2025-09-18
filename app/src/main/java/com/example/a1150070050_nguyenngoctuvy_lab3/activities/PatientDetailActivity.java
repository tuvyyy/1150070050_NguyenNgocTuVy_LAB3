package com.example.a1150070050_nguyenngoctuvy_lab3.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1150070050_nguyenngoctuvy_lab3.R;
import com.example.a1150070050_nguyenngoctuvy_lab3.database.DatabaseHandler;
import com.example.a1150070050_nguyenngoctuvy_lab3.model.Patient;

public class PatientDetailActivity extends AppCompatActivity {

    private EditText etName, etPhone;
    private Button btnUpdate;
    private DatabaseHandler db;
    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        etName = findViewById(R.id.etDetailName);
        etPhone = findViewById(R.id.etDetailPhone);
        btnUpdate = findViewById(R.id.btnUpdatePatient);

        db = new DatabaseHandler(this);

        // Lấy ID được truyền từ Adapter
        patientId = getIntent().getIntExtra("id", -1);

        if (patientId != -1) {
            Patient patient = db.getPatient(patientId);
            if (patient != null) {
                etName.setText(patient.getName());
                etPhone.setText(patient.getPhone());
            }
        }

        // Xử lý nút cập nhật
        btnUpdate.setOnClickListener(v -> {
            String newName = etName.getText().toString().trim();
            String newPhone = etPhone.getText().toString().trim();

            if (!newName.isEmpty() && !newPhone.isEmpty()) {
                Patient updatedPatient = new Patient(patientId, newName, newPhone);
                db.updatePatient(updatedPatient);
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish(); // quay lại màn hình trước
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
