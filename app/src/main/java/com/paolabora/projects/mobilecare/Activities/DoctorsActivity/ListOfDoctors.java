package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Adapters.DoctorsAdapter;
import com.paolabora.projects.mobilecare.Modules.DoctorModule;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;

public class ListOfDoctors extends AppCompatActivity {

    private DatabaseReference reference;
    private List<DoctorModule> doctorModuleList;
    private RecyclerView recyclerView;
    private DoctorsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_doctors);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        recyclerView = findViewById(R.id.list_of_doctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListOfDoctors.this));
        recyclerView.setHasFixedSize(true);

        clearList();
        getDoctors();

    }

    private void getDoctors() {

        reference.child("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    DoctorModule doctors = snapshots.getValue(DoctorModule.class);

                    doctorModuleList.add(doctors);
                }
                Log.d("the number of doctors", doctorModuleList.size()+"");
                adapter = new DoctorsAdapter(ListOfDoctors.this, doctorModuleList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearList() {
        if (doctorModuleList != null) {
            doctorModuleList.clear();

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        doctorModuleList = new ArrayList<>();
    }
}
