package com.paolabora.projects.mobilecare.Activities.PatientActivity;

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
import com.paolabora.projects.mobilecare.Adapters.ScheduleSlotsAdapter;
import com.paolabora.projects.mobilecare.Modules.AvailabilityModule;
import com.paolabora.projects.mobilecare.Modules.ScheduleSlotList;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;

public class ViewScheduleSlots extends AppCompatActivity {

    private DatabaseReference reference;
    private List<AvailabilityModule> scheduleList;
    private RecyclerView recyclerView;
    private ScheduleSlotsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule_slots);

        reference = FirebaseDatabase.getInstance().getReference("Appointments");

        recyclerView = findViewById(R.id.schedule_slot_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewScheduleSlots.this));
        recyclerView.setHasFixedSize(true);

        clearList();
        getDoctors();

    }

    private void getDoctors() {

        reference.child("Doctors Availability").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    AvailabilityModule availability = snapshots.getValue(AvailabilityModule.class);

                    scheduleList.add(availability);
                }
                Log.d("the number of slots", scheduleList.size()+"");
                adapter = new ScheduleSlotsAdapter(ViewScheduleSlots.this, scheduleList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearList() {
        if (scheduleList != null) {
            scheduleList.clear();

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        scheduleList = new ArrayList<>();
    }
}
