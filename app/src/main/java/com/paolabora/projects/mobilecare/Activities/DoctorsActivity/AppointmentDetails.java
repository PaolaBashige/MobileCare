package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Modules.AppointmentModule;
import com.paolabora.projects.mobilecare.R;

public class AppointmentDetails extends AppCompatActivity {
    private TextView doctorName, patientName, appointmentReason, appDateSet, appTimeSet,
            medications, symptomsTxt, symptomsDesc;
    public String doctorStr, patientStr, reasonStr, dateStr, timeStr, medicationStr,
            symptomsStr, descriptionStr, patientId, doctorId, fUser;
    private DatabaseReference reference, userReference;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        intent = getIntent();

        reference = FirebaseDatabase.getInstance().getReference("Appointments");
        userReference = FirebaseDatabase.getInstance().getReference("Users");

        doctorName = findViewById(R.id.doctor_name);
        patientName = findViewById(R.id.patient_name);
        appointmentReason = findViewById(R.id.reason_visit);
        appDateSet = findViewById(R.id.availableDates);
        appTimeSet = findViewById(R.id.availableHours);
        medications = findViewById(R.id.medic_text);
        symptomsTxt = findViewById(R.id.symptoms_text);
        symptomsDesc = findViewById(R.id.symptoms_desc);

        reasonStr = intent.getStringExtra("appointmentReason");
        dateStr = intent.getStringExtra("date");
        timeStr = intent.getStringExtra("time");
        medicationStr = intent.getStringExtra("medications");
        symptomsStr = intent.getStringExtra("symptoms");
        descriptionStr = intent.getStringExtra("symptomDesc");
        patientId = intent.getStringExtra("patientId");
        doctorId = intent.getStringExtra("doctorId");

        fUser = mUser.getUid();

        viewDetails();
    }

    private void viewDetails() {userReference.child("Doctors").child(doctorId).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            doctorStr = snapshot.child("doctorsFistName").getValue().toString() + " " +
                    snapshot.child("doctorsLastName").getValue().toString();
            if(!doctorStr.isEmpty())
                doctorName.setText(doctorStr);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

        userReference.child("Patients").child(patientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientStr = snapshot.child("patientFirstName").getValue().toString() + " " +
                        snapshot.child("patientMiddleName").getValue().toString() + " " +
                        snapshot.child("patientLastName").getValue().toString();
                if(!patientStr.isEmpty())
                    patientName.setText(patientStr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference.child("Patient Appointments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    AppointmentModule module = snapshot1.getValue(AppointmentModule.class);

                    if (module.getDoctorId().equals(doctorId) || module.getDoctorId().equals(fUser) &&
                            module.getPatientId().equals(patientId) || module.getDoctorId().equals(fUser)){


                        appointmentReason.setText(reasonStr);
                        appDateSet.setText(dateStr);
                        appTimeSet.setText(timeStr);
                        medications.setText(medicationStr);
                        symptomsTxt.setText(symptomsStr);
                        symptomsDesc.setText(descriptionStr);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
