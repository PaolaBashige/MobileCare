package com.paolabora.projects.mobilecare.Activities.PatientActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.paolabora.projects.mobilecare.Modules.DoctorModule;
import com.paolabora.projects.mobilecare.Modules.PatientModule;
import com.paolabora.projects.mobilecare.Modules.SymptomModule;
import com.paolabora.projects.mobilecare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UploadSymptoms extends AppCompatActivity {
    private EditText symptoms, symptomDesc, medications, latestTravels, lifeChanges;
    private TextView doctorsName;
    private RadioGroup medicAns, travelAns, changesAns;
    private RadioButton medicBtn, travelBtn, changeBtn;
    private Button uploadSymptoms;
    private Date dateTime;


    private FirebaseDatabase database;
    private DatabaseReference reference, docReference;
    private FirebaseUser auth;
    private String doctorId;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_symptoms);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Patient Ongoing History");
        auth = FirebaseAuth.getInstance().getCurrentUser();

        symptoms = findViewById(R.id.symptom_list);
        symptomDesc = findViewById(R.id.brief_description);
        medications = findViewById(R.id.medic_list);
        latestTravels = findViewById(R.id.trav_dest);
        lifeChanges = findViewById(R.id.change_desc);
        doctorsName = findViewById(R.id.doctor_name);

        medicAns = findViewById(R.id.medic_selec);
        int medicBtnId = medicAns.getCheckedRadioButtonId();
        medicBtn = findViewById(medicBtnId);

        travelAns = findViewById(R.id.trav_selec);
        int travelBtnId = travelAns.getCheckedRadioButtonId();
        travelBtn = findViewById(travelBtnId);

        changesAns = findViewById(R.id.change_selec);
        int changeBtnId = changesAns.getCheckedRadioButtonId();
        changeBtn = findViewById(changeBtnId);

        intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");

        docReference = FirebaseDatabase.getInstance().getReference("Users").child("Doctors")
                .child(doctorId);

        docReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoctorModule module = snapshot.getValue(DoctorModule.class);
                doctorsName.setText(module.getDoctorsFistName() + " " + module.getDoctorsLastName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        uploadSymptoms = findViewById(R.id.send_symp_btn);
        uploadSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPatientSymptoms();
            }
        });
    }

    public void travelChecked (View view) {
        int travelBtn = travelAns.getCheckedRadioButtonId();
        travelAns.findViewById(travelBtn);
    }

    public void medChecked(View v) {
        int medicBtnId = medicAns.getCheckedRadioButtonId();
        medicAns.findViewById(medicBtnId);
    }

    public void changeChecked (View v) {
        int changeBtnId = changesAns.getCheckedRadioButtonId();
        changesAns.findViewById(changeBtnId);
    }


    private void uploadPatientSymptoms() {

        String symptomList = symptoms.getText().toString();
        String symptomsDesc = symptomDesc.getText().toString();
        String medicationList = medications.getText().toString();
        String latestTravel = latestTravels.getText().toString();
        String lifeChange = lifeChanges.getText().toString();
        String medication = medicBtn.getText().toString();
        String travelHistory = travelBtn.getText().toString();
        String recentChanges = changeBtn.getText().toString();
        String status = "NOT Examined";

        dateTime = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a", Locale.getDefault());
        String uploadTime = df.format(new Date());

        String senderId = auth.getUid();
        String uploadId = reference.push().getKey();

        SymptomModule symptomModule = new SymptomModule(symptomList, symptomsDesc, medication,
                medicationList, travelHistory, latestTravel, recentChanges, lifeChange, senderId,
                doctorId, uploadTime, status, uploadId);

        reference.child("Symptom(s)").child(uploadId).setValue(symptomModule).
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UploadSymptoms.this,
                            "Your symptoms have been uploaded",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UploadSymptoms.this,
                            "Sorry! Your symptoms were not uploaded",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
