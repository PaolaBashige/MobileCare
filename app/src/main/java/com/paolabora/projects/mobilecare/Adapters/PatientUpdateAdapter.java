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

import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.ViewPatientUpdate;
import com.paolabora.projects.mobilecare.Activities.PatientActivity.UploadAnUpdate;
import com.paolabora.projects.mobilecare.Modules.DoctorUpdate;
import com.paolabora.projects.mobilecare.Modules.PatientUpdate;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

public class PatientUpdateAdapter extends RecyclerView.Adapter<PatientUpdateAdapter.PatientUpdateViewHolder>{
    private static final String TAG = "PatientUpdateAdapter";
    private List<PatientUpdate> update;
    private Context context;

    public PatientUpdateAdapter(Context context, List<PatientUpdate> update) {
        this.update = update;
        this.context = context;
    }

    @NonNull
    @Override
    public PatientUpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.patient_update_item, parent,false);
        return new PatientUpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatientUpdateViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.patientName.setText("Update from: " + update.get(position).getPatientName());
        holder.patientDiagnosis.setText("The diagnosis was: " + update.get(position).getDocDiagnosis());

    }

    @Override
    public int getItemCount() {
        return update.size();
    }

    public class PatientUpdateViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, patientDiagnosis;

        public PatientUpdateViewHolder(@NonNull View itemView) {
            super(itemView);

            patientName = itemView.findViewById(R.id.patient_name);
            patientDiagnosis = itemView.findViewById(R.id.patient_diagnosis);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, ViewPatientUpdate.class);
                        intent.putExtra("patientName", update.get(position).getPatientName());
                        intent.putExtra("patientId", update.get(position).getSenderId());
                        intent.putExtra("doctorId", update.get(position).getReceiverId());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
