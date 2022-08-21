package com.paolabora.projects.mobilecare.Activities.PatientActivity;

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
import com.paolabora.projects.mobilecare.Adapters.DocUpdateAdapter;
import com.paolabora.projects.mobilecare.Modules.DoctorModule;
import com.paolabora.projects.mobilecare.Modules.DoctorUpdate;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;

public class DocUpdateList extends AppCompatActivity {

    private DatabaseReference updatesReference, reference;
    private FirebaseUser patAuth;
    private String doctorId, patId;
    private RecyclerView recyclerView;
    private DocUpdateAdapter adapter;
    private List<String> modules = new ArrayList<>();
    private List<DoctorModule> doctorModules = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_update_list);

        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");

        reference = FirebaseDatabase.getInstance().getReference("Users");

        recyclerView = findViewById(R.id.list_of_doctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(DocUpdateList.this));
        recyclerView.setHasFixedSize(true);

        patAuth = FirebaseAuth.getInstance().getCurrentUser();
        patId = patAuth.getUid();

        updatesReference = FirebaseDatabase.getInstance().getReference("Patient Ongoing History")
                .child("Doctor's Update");
        updatesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    DoctorUpdate update = snapshot1.getValue(DoctorUpdate.class);

                    if (update.getReceiverId().equals(patId)) {
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

        reference.child("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorModules.clear();

                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    DoctorModule doctor = snapshots.getValue(DoctorModule.class);

                    for (String id : modules) {
                        if (doctor.getDoctorId().equals(id)) {
                            if (doctorModules.size() != 0) {
                                for (DoctorModule doc : doctorModules) {
                                    if (!doc.getDoctorId().equals(doctor.getDoctorId())) {
                                        doctorModules.add(doctor);
                                    }
                                }
                            } else {
                                doctorModules.add(doctor);
                            }
                        }
                    }
                }
                Log.d("Number of Patients", doctorModules.size()+"");
                adapter = new DocUpdateAdapter(DocUpdateList.this, doctorModules);
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
