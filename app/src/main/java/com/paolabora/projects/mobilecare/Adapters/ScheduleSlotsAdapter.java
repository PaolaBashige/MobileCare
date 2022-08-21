package com.paolabora.projects.mobilecare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Activities.PatientActivity.BookingAppointment;
import com.paolabora.projects.mobilecare.Modules.AvailabilityModule;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

public class ScheduleSlotsAdapter extends RecyclerView.Adapter<ScheduleSlotsAdapter.SlotsViewModel>  {
    private static final String TAG = "ScheduleSlotsAdapter";
    private Context context;
    public List<AvailabilityModule> slotLists;
    private String doctorId;
    private DatabaseReference reference;
    private DatabaseReference slotRef = FirebaseDatabase.getInstance().getReference("Appointments");

    public ScheduleSlotsAdapter(Context context, List<AvailabilityModule> slotLists) {
        this.context = context;
        this.slotLists = slotLists;
    }

    @NonNull
    @Override
    public SlotsViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.schedule_slot, parent,false);
        return new SlotsViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SlotsViewModel holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        doctorId = slotLists.get(position).getAppDoctorId();
        reference = FirebaseDatabase.getInstance()
                .getReference("Users").child("Doctors").child(doctorId);


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.doctorName.setText(snapshot.child("doctorsFistName").getValue(String.class) +
                        " " + snapshot.child("doctorsLastName").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addListenerForSingleValueEvent(listener);

        holder.dataAndTime.setText(slotLists.get(position).getDate() + " - " +
                slotLists.get(position).getTime());
        holder.status.setText(slotLists.get(position).getScheduleStatus());

    }

    @Override
    public int getItemCount() {
        return slotLists.size();
    }

    public class SlotsViewModel extends RecyclerView.ViewHolder {
        TextView doctorName, dataAndTime, status;

        public SlotsViewModel(@NonNull View itemView) {
            super(itemView);

            doctorName = itemView.findViewById(R.id.doctor_name);
            dataAndTime = itemView.findViewById(R.id.date_and_time_schedule);
            status = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        if (slotLists.get(position).getScheduleStatus().equals("Open")) {
                            Intent intent = new Intent(context, BookingAppointment.class);
                            intent.putExtra("doctorName", slotLists.get(position).getAppDoctorName());
                            intent.putExtra("doctorId", slotLists.get(position).getAppDoctorId());
                            intent.putExtra("dateAvailable", slotLists.get(position).getDate());
                            intent.putExtra("timeSelected", slotLists.get(position).getTime());
                            intent.putExtra("slotStatus", slotLists.get(position).getScheduleStatus());
                            intent.putExtra("scheduleId", slotLists.get(position).getAppId());

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context.getApplicationContext(),
                                    "Sorry! This appointment slot has been booked",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

}
