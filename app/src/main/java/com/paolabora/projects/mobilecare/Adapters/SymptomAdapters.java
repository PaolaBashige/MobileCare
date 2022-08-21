package com.paolabora.projects.mobilecare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.AboutTheDoctor;
import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.ViewSymptoms;
import com.paolabora.projects.mobilecare.Modules.PatientModule;
import com.paolabora.projects.mobilecare.Modules.SymptomModule;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

public class  SymptomAdapters extends RecyclerView.Adapter<SymptomAdapters.SymptomViewHolder>  {
    private static final String TAG = "BooksAdapter";
    private static final int MESSAGE_SENDER = 0;
    private static final int MESSAGE_RECEIVER = 1;

    private Context context;
    public List<SymptomModule> symptomModules;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    private DatabaseReference symReference = FirebaseDatabase.getInstance().getReference("Symptom(s)");
    private String patientId, doctorId, patientName, patId;
    private FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();

    public SymptomAdapters(Context context, List<SymptomModule> symptomModules) {
        this.context = context;
        this.symptomModules = symptomModules;
    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.patient_item, parent,false);
        return new SymptomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SymptomViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.dateAndTime.setText(symptomModules.get(position).getUploadTime());
        holder.status.setText(symptomModules.get(position).getSymptomsStatus());
        holder.patientNameTxt.setText(patientName);


        holder.toolbar.inflateMenu(R.menu.user_profile_menu);
        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about_doctors:
                        context.startActivity(new Intent(context, AboutTheDoctor.class));
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return symptomModules.size();
    }

    public class SymptomViewHolder extends RecyclerView.ViewHolder {
        TextView patientNameTxt, patientBirthDate, patientGender, dateAndTime, status;
        Toolbar toolbar;

        public SymptomViewHolder(@NonNull View itemView) {
            super(itemView);

            patientNameTxt = itemView.findViewById(R.id.patient_name);
            patientBirthDate = itemView.findViewById(R.id.patient_date_of_birth);
            patientGender = itemView.findViewById(R.id.patient_gender);
            toolbar = itemView.findViewById(R.id.about_pat_menu);
            status = itemView.findViewById(R.id.status);
            //dateAndTime = itemView.findViewById(R.id.date_and_time_posted);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (symptomModules.get(position).getSymptomsStatus().equals("NOT Examined")) {
                            status.setTextColor(Color.parseColor("red"));

                            Intent intent = new Intent(context, ViewSymptoms.class);
                            intent.putExtra("doctorId", symptomModules.get(position).getReceiver());

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            status.setTextColor(Color.parseColor("green"));
                        }
                    }
                }
            });

            symReference.child("sender").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    patientId = snapshot.getValue().toString();

                    /*for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        patId = snapshot1.child("sender").getValue(String.class);
                    }*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            reference.child("Patients").child(patientId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        PatientModule module = snapshot2.getValue(PatientModule.class);

                        patientName = module.getPatientFirstName() + " " +
                                module.getPatientMiddleName() + " " + module.getPatientLastName();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}
