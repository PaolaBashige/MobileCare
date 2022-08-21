package com.paolabora.projects.mobilecare.Activities.PatientActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paolabora.projects.mobilecare.Modules.AppointmentModule;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

public class BookingAppointment extends AppCompatActivity {
    private Intent intent;
    private TextView doctorName, dateSet, timeSet;
    private EditText symptoms, symptomDesc, medications;
    private Spinner appointmentReason;
    private String date, time, docName, doctorId, status, scheduleId, patientName, patientId, appReason;
    private DatabaseReference scheduleReference;
    private FirebaseUser mUser;
    private List<String> dates, times, reasons;
    private Button bookSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appointment);

        doctorName = findViewById(R.id.doctor_name);
        symptoms = findViewById(R.id.symptomLlist);
        symptomDesc = findViewById(R.id.briefDescription);
        medications = findViewById(R.id.medicList);
        dateSet = findViewById(R.id.availableDates);
        timeSet = findViewById(R.id.availableHours);
        appointmentReason = findViewById(R.id.reason_visit);
        bookSlot = findViewById(R.id.button);

        scheduleReference = FirebaseDatabase.getInstance().getReference("Appointments");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        patientId = mUser.getUid();

        intent = getIntent();
        date = intent.getStringExtra("dateAvailable");
        time = intent.getStringExtra("timeSelected");
        docName = intent.getStringExtra("doctorName");
        doctorId = intent.getStringExtra("doctorId");
        scheduleId = intent.getStringExtra("scheduleId");

        doctorName.setText(docName);
        dateSet.setText(date);
        timeSet.setText(time);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource
                (this, R.array.appointmentReason, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        appointmentReason.setAdapter(spinnerAdapter);
        appointmentReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appReason = parent.getItemAtPosition(position).toString();
                appointmentReason.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bookSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment();
            }
        });
    }

    private void bookAppointment() {
        appointmentReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appReason = parent.getItemAtPosition(position).toString();
                appointmentReason.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String patientSymptoms = symptoms.getText().toString();
        String sympDesc = symptomDesc.getText().toString();
        String meds = medications.getText().toString();

        AppointmentModule module = new AppointmentModule(docName, doctorId, date, time,
                patientName, patientId, appReason, patientSymptoms, sympDesc, meds);
        String uploadKey = scheduleReference.push().getKey();

        scheduleReference.child("Patient Appointments").child(uploadKey).setValue(module)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(BookingAppointment.this, "Your appointment has been booked",
                                    Toast.LENGTH_SHORT).show();
                            updateStatus();
                        } else {
                            Toast.makeText(BookingAppointment.this, "Your appointment was not booked",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateStatus() {
        status = "Booked";
        scheduleReference.child("Doctors Availability").child(scheduleId).child("scheduleStatus")
                .setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}
