package com.paolabora.projects.mobilecare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.ViewSymptoms;
import com.paolabora.projects.mobilecare.Activities.PatientActivity.AboutThePatient;
import com.paolabora.projects.mobilecare.Modules.PatientModule;
import com.paolabora.projects.mobilecare.Modules.SymptomModule;
import com.paolabora.projects.mobilecare.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>  {
    private static final String TAG = "PatientsAdapter";
    private Context context;
    public List<PatientModule> patientModules;
    public List<SymptomModule> symptomModules;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    private DatabaseReference symReference = FirebaseDatabase.getInstance()
            .getReference("Patient Ongoing History").child("Symptom(s)");
    private String dateTime, doctorId;

    public PatientsAdapter(Context context, List<PatientModule> patientModules) {
        this.context = context;
        this.patientModules = patientModules;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.patient_item, parent,false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatientViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        SymptomModule module = new SymptomModule();

        holder.patientName.setText(patientModules.get(position).getPatientFirstName() + " " +
                patientModules.get(position).getPatientMiddleName() + " " +
                patientModules.get(position).getPatientLastName());
        /*holder.status.setText("NOT Examined");

        symReference.child("").child("uploadTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //holder.dateAndTime.setText(snapshot.getValue().toString());

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        SymptomModule symptomModule = snapshot1.getValue(SymptomModule.class);
                        //dateTime = symptomModule.getUploadTime();
                        holder.dateAndTime.setText(symptomModule.getUploadTime());
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        holder.toolbar.inflateMenu(R.menu.about_patient);
        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.patient_info:
                        context.startActivity(new Intent(context, AboutThePatient.class));
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
        return patientModules.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, patientBirthDate, patientGender, dateAndTime, status;
        Toolbar toolbar;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            patientName = itemView.findViewById(R.id.patient_name);
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

                        Intent intent = new Intent(context, ViewSymptoms.class);
                        intent.putExtra("patientName", patientModules.get(position).getPatientFirstName());
                        intent.putExtra("patientId", patientModules.get(position).getPatientId());
                        intent.putExtra("doctorId", doctorId);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        /*if (status.getText().equals("NOT Examined")) {

                            Intent intent = new Intent(context, ViewSymptoms.class);
                            intent.putExtra("patientName", patientModules.get(position).getPatientFirstName());
                            intent.putExtra("patientId", patientModules.get(position).getPatientId());
                            intent.putExtra("doctorId", doctorId);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        } else {
                            Toast.makeText(context.getApplicationContext(),
                                    "Symptoms have been viewed", Toast.LENGTH_SHORT);
                        }*/
                    }
                }
            });

            reference.child("Doctors").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    doctorId = snapshot.getKey();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
