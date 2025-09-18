package com.example.a1150070050_nguyenngoctuvy_lab3.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1150070050_nguyenngoctuvy_lab3.R;
import com.example.a1150070050_nguyenngoctuvy_lab3.activities.PatientActivity;
import com.example.a1150070050_nguyenngoctuvy_lab3.activities.PatientDetailActivity;
import com.example.a1150070050_nguyenngoctuvy_lab3.model.Patient;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private Context context;
    private List<Patient> patientList;
    private OnDeleteListener deleteListener;

    public interface OnDeleteListener {
        void onDelete(int patientId);
    }

    public PatientAdapter(Context context, List<Patient> patientList, OnDeleteListener listener) {
        this.context = context;
        this.patientList = patientList;
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.tvName.setText(patient.getName());
        holder.tvPhone.setText(patient.getPhone());

        // click: xem chi tiết
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PatientDetailActivity.class);
            intent.putExtra("id", patient.getId());
            context.startActivity(intent);
        });

        // long click: xóa
        holder.itemView.setOnLongClickListener(v -> {
            if (deleteListener != null) deleteListener.onDelete(patient.getId());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPatientName);
            tvPhone = itemView.findViewById(R.id.tvPatientPhone);
        }
    }
}
