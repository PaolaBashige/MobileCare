package com.paolabora.projects.mobilecare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paolabora.projects.mobilecare.Activities.PatientActivity.ViewDoctorUpdate;
import com.paolabora.projects.mobilecare.Modules.DoctorUpdate;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

public class DoctorUpdateAdapter extends RecyclerView.Adapter<DoctorUpdateAdapter.DoctorUpdateViewHolder>{
    private final static String TAG = "DoctorUpdateAdapter";
    private List<DoctorUpdate> updateList;
    private Context context;

    public DoctorUpdateAdapter(Context context, List<DoctorUpdate> updateList) {
        this.updateList = updateList;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorUpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.doctor_update_item, parent,false);
        return new DoctorUpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorUpdateViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.doctorName.setText("Diagnosis by Dr. " + updateList.get(position).getDoctorName());
        holder.diagnosis.setText("Diagnosis: " + updateList.get(position).getDiagnosis());
    }

    @Override
    public int getItemCount() {
        return updateList.size();
    }

    public class DoctorUpdateViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName, diagnosis;

        public DoctorUpdateViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorName = itemView.findViewById(R.id.doctor_name);
            diagnosis = itemView.findViewById(R.id.patient_diagnosis);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, ViewDoctorUpdate.class);
                        intent.putExtra("doctorName", updateList.get(position).getDoctorName());
                        intent.putExtra("doctorId", updateList.get(position).getSenderId());
                        intent.putExtra("patientId", updateList.get(position).getReceiverId());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
