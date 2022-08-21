package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Modules.AvailabilityModule;
import com.paolabora.projects.mobilecare.Modules.DoctorModule;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;

public class SetAvailability extends AppCompatActivity {
    private DatePicker setDate;
    private TimePicker setTime;
    private Button setAvailability;

    private DatabaseReference reference, user;
    private FirebaseUser mUser;
    private String doctorId, uploadKey, doctorName, slotStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_availability);

        setAvailability = findViewById(R.id.available_bth);
        setAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppointmentAvailability();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Appointments");
        user = FirebaseDatabase.getInstance().getReference("Users");
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        setDate = findViewById(R.id.set_date);
        setDate.init(2020, 01 , 01, null);

        setTime = findViewById(R.id.set_time);
        setTime.setEnabled(true);
        setTime.setIs24HourView(true);
        setTime.setMinute(59);
    }

    private void setAppointmentAvailability() {
        final String dateAvailable = setDate.getDayOfMonth()+"/" + setDate.getMonth()+
                "/"+setDate.getYear();
        final String timeAvailable = (String.format("%02d", setTime.getHour()) + ":" +
                (String.format("%02d", setTime.getMinute())));

        new AlertDialog.Builder(this).setTitle("Availability Information").setMessage(
                "The details of your appointment:\n" +
                        "date: " + dateAvailable + "\nTime: " + timeAvailable)
                .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveData();
            }
        }).show();
    }

    private void saveData() {
        String dateAvailable = setDate.getDayOfMonth()+"/" + setDate.getMonth()+
                "/"+setDate.getYear();
        String timeAvailable = (String.format("%02d", setTime.getHour()) + ":" +
                (String.format("%02d", setTime.getMinute())));
        slotStatus = "Open";

        doctorId = mUser.getUid();
        user.child("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DoctorModule doctorModule = snapshot1.getValue(DoctorModule.class);

                    doctorName = doctorModule.getDoctorsFistName() + " " + doctorModule.getDoctorsLastName();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        uploadKey = reference.push().getKey();

        AvailabilityModule module = new AvailabilityModule(doctorName, doctorId, timeAvailable,
                dateAvailable, slotStatus, uploadKey);

        reference.child("Doctors Availability").child(uploadKey).setValue(module).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(SetAvailability.this,
                            "Your schedule has been uploaded",
                            Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(SetAvailability.this,
                            "Sorry! Your schedule has NOT been uploaded",
                            Toast.LENGTH_LONG).show();
            }
        });
    }
}
