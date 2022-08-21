package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Adapters.AppointmentAdapter;
import com.paolabora.projects.mobilecare.Modules.AppointmentModule;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends AppCompatActivity {

    private DatabaseReference reference;
    private List<AppointmentModule> modules;
    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private String fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        reference = FirebaseDatabase.getInstance().getReference("Appointments");

        fUser = mUser.getUid();
        recyclerView = findViewById(R.id.schedule_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(Schedule.this));
        recyclerView.setHasFixedSize(true);

        clearList();
        getSchedule();

    }

    private void getSchedule() {

        reference.child("Patient Appointments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    AppointmentModule module = snapshots.getValue(AppointmentModule.class);

                    if (fUser.equals(module.getDoctorId()) || fUser.equals(module.getPatientId())) {
                        modules.add(module);
                    }
                }
                Log.d("The No of booked slots", modules.size()+"");
                adapter = new AppointmentAdapter(Schedule.this, modules);
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
