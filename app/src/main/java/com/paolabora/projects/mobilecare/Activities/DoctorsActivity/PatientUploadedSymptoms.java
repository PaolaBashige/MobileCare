package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Adapters.PatientsAdapter;
import com.paolabora.projects.mobilecare.Modules.DoctorModule;
import com.paolabora.projects.mobilecare.Modules.PatientModule;
import com.paolabora.projects.mobilecare.Modules.SymptomModule;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;

public class PatientUploadedSymptoms extends AppCompatActivity {
    private DatabaseReference symptomReference, reference;
    private FirebaseUser docAuth;
    private String patientId, docId, receiver, sender;
    private RecyclerView recyclerView;
    private PatientsAdapter adapter;
    private List<String> modules = new ArrayList<>();
    private List<PatientModule> patientModule = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_list);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        recyclerView = findViewById(R.id.list_of_patients);
        recyclerView.setLayoutManager(new LinearLayoutManager(PatientUploadedSymptoms.this));
        recyclerView.setHasFixedSize(true);

        docAuth = FirebaseAuth.getInstance().getCurrentUser();
        docId = docAuth.getUid();

        symptomReference = FirebaseDatabase.getInstance().getReference("Patient Ongoing History")
                .child("Symptom(s)");
        symptomReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    SymptomModule symptomModule = snapshot1.getValue(SymptomModule.class);

                    if (symptomModule.getReceiver().equals(docId)) {
                        modules.add(symptomModule.getSender());
                    }
                }

                Log.d("Patients with symptoms", modules.size()+"");
                displaysUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void displaysUsers() {

        reference.child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientModule.clear();

                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    PatientModule patients = snapshots.getValue(PatientModule.class);

                    for (String id : modules) {
                        if (patients.getPatientId().equals(id)) {
                           // patientModule.add(patients);

                            if (patientModule.size() != 0) {
                                patientModule.add(patients);

                                /*for (PatientModule patient : patientModule) {
                                     if (!patients.getPatientId().equals(patient.getPatientId())) {
                                        patientModule.add(patients);
                                    }
                                }*/
                            } else {
                                patientModule.add(patients);
                            }
                        }
                    }
                }

                Log.d("Number of Patients", patientModule.size()+"");
                adapter = new PatientsAdapter(PatientUploadedSymptoms.this, patientModule);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearList() {
        if (modules != null) {
            modules.clear();

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        modules = new ArrayList<>();
    }

}
