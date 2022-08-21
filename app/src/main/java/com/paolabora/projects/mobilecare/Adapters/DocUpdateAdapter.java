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

import com.paolabora.projects.mobilecare.Activities.PatientActivity.UpdatesList;
import com.paolabora.projects.mobilecare.Modules.DoctorModule;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

public class DocUpdateAdapter extends RecyclerView.Adapter<DocUpdateAdapter.DocViewHolder>{
    private static final String TAG = "BooksAdapter";
    private Context context;
    public List<DoctorModule> doctorModules;

    public DocUpdateAdapter(Context context, List<DoctorModule> doctorModules) {
        this.context = context;
        this.doctorModules = doctorModules;
    }

    @NonNull
    @Override
    public DocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.doc_update_item, parent,false);
        return new DocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.doctorsName.setText(doctorModules.get(position).getDoctorsFistName() + " " +
                doctorModules.get(position).getDoctorsLastName());
    }

    @Override
    public int getItemCount() {
        return doctorModules.size();
    }

    public class DocViewHolder extends RecyclerView.ViewHolder {
        TextView doctorsName;

        public DocViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorsName = itemView.findViewById(R.id.doctor_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, UpdatesList.class);
                        intent.putExtra("doctorName", doctorModules.get(position).getDoctorsFistName());
                        intent.putExtra("doctorId", doctorModules.get(position).getDoctorId());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
