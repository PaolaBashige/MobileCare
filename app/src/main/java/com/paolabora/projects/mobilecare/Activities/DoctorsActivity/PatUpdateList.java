package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

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
import com.paolabora.projects.mobilecare.Adapters.PatUpdateAdapter;
import com.paolabora.projects.mobilecare.Modules.PatientModule;
import com.paolabora.projects.mobilecare.Modules.PatientUpdate;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;

public class PatUpdateList extends AppCompatActivity {

    private DatabaseReference updatesReference, reference;
    private FirebaseUser docAuth;
    private String patientId, docId;
    private RecyclerView recyclerView;
    private PatUpdateAdapter adapter;
    private List<String> modules = new ArrayList<>();
    private List<PatientModule> patientModule = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pat_update_list);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        recyclerView = findViewById(R.id.list_of_patients);
        recyclerView.setLayoutManager(new LinearLayoutManager(PatUpdateList.this));
        recyclerView.setHasFixedSize(true);

        docAuth = FirebaseAuth.getInstance().getCurrentUser();
        docId = docAuth.getUid();

        updatesReference = FirebaseDatabase.getInstance().getReference("Patient Ongoing History")
                .child("Updates From Patient");
        updatesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    PatientUpdate update = snapshot1.getValue(PatientUpdate.class);

                    if (update.getReceiverId().equals(docId)) {
                        modules.add(update.getSenderId());
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
                            if (patientModule.size() != 0) {
                                for (PatientModule patient : patientModule) {
                                    if (!patients.getPatientId().equals(patient.getPatientId())) {
                                        patientModule.add(patients);
                                    }
                                }
                            } else {
                                patientModule.add(patients);
                            }
                        }
                    }
                }
                Log.d("Number of Patients", patientModule.size()+"");
                adapter = new PatUpdateAdapter(PatUpdateList.this, patientModule);
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
