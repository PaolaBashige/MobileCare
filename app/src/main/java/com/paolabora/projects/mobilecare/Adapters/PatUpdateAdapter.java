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

import com.paolabora.projects.mobilecare.Activities.UpdateLists;
import com.paolabora.projects.mobilecare.Modules.PatientModule;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

public class PatUpdateAdapter extends RecyclerView.Adapter<PatUpdateAdapter.PatViewHolder>{
    private static final String TAG = "BooksAdapter";
    private Context context;
    public List<PatientModule> patientModules;

    public PatUpdateAdapter(Context context, List<PatientModule> patientModules) {
        this.context = context;
        this.patientModules = patientModules;
    }

    @NonNull
    @Override
    public PatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pat_update_item, parent,false);
        return new PatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.patientName.setText(patientModules.get(position).getPatientFirstName() + " " +
                patientModules.get(position).getPatientLastName());
    }

    @Override
    public int getItemCount() {
        return patientModules.size();
    }

    public class PatViewHolder extends RecyclerView.ViewHolder {
        TextView patientName;

        public PatViewHolder(@NonNull View itemView) {
            super(itemView);

            patientName = itemView.findViewById(R.id.patient_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, UpdateLists.class);
                        intent.putExtra("patientName", patientModules.get(position)
                                .getPatientFirstName() + " " + patientModules.get(position)
                                .getPatientLastName());
                        intent.putExtra("patientId", patientModules.get(position).getPatientId());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

}
