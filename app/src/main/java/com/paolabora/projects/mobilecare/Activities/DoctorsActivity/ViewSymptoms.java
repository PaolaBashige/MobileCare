package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class ViewSymptoms extends AppCompatActivity {
    private TextView patientSymptoms, patientSymptomsDesc, patientMedicAns, patientMedicLis,
            patientTravelAns, patientTravelHis, patientLifeChangeAns, patientLifeChangeHist, patientName;

    private DatabaseReference reference, patientReference, symptomsReference, updateReference;
    private FirebaseUser doctorUser = FirebaseAuth.getInstance().getCurrentUser();
    private Button respondToPatient, goBackButton;

    private Intent patientIntent;
    private String patientId, doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_symptoms);

        patientIntent = getIntent();
        patientId = patientIntent.getStringExtra("patientId");

        doctorId = doctorUser.getUid();

        patientSymptoms = findViewById(R.id.patient_symptom_list);
        patientSymptomsDesc = findViewById(R.id.symptoms_brief_description);
        patientMedicAns = findViewById(R.id.patient_medic_selec);
        patientMedicLis = findViewById(R.id.patient_medic_list);
        patientTravelAns = findViewById(R.id.patient_trav_selec);
        patientTravelHis = findViewById(R.id.patient_trav_dest);
        patientLifeChangeAns = findViewById(R.id.patient_change_selec);
        patientLifeChangeHist = findViewById(R.id.patient_change_desc);
        patientName = findViewById(R.id.patient_name);
        respondToPatient = findViewById(R.id.update_patient_symp_btn);
        respondToPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSymptoms.this, SendUpdate.class);
                intent.putExtra("patientName", patientName.getText().toString());
                intent.putExtra("patientId", patientId);
                intent.putExtra("patientSymptoms", patientSymptoms.getText().toString());
                intent.putExtra("patientSymptomsDesc", patientSymptomsDesc.getText().toString());
                intent.putExtra("doctorId", doctorId);
                //intent.putExtra("symptomId", symptomModule.getSymptomId());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        goBackButton = findViewById(R.id.go_back_btn);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewSymptoms.this, SelectService.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");
        symptomsReference = FirebaseDatabase.getInstance().getReference("Patient Ongoing History")
                .child("Symptom(s)");
        patientReference = FirebaseDatabase.getInstance().getReference("Users").child("Patients");
        updateReference = FirebaseDatabase.getInstance().getReference("Patient Ongoing History")
                .child("Updates");

        patientReference.child(patientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PatientModule module = snapshot.getValue(PatientModule.class);
                patientName.setText(module.getPatientFirstName() + " " + module.getPatientMiddleName() +
                        " " + module.getPatientLastName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        symptomsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    SymptomModule symptomModule = snapshot1.getValue(SymptomModule.class);


                    if (symptomModule.getReceiver().equals(doctorId) &&
                            symptomModule.getSender().equals(patientId)) {
                        patientSymptoms.setText(symptomModule.getSymptoms());
                        patientSymptomsDesc.setText(symptomModule.getSymptomDesc());
                        patientMedicAns.setText(symptomModule.getMedicAns());
                        patientMedicLis.setText(symptomModule.getMedications());
                        patientTravelAns.setText(symptomModule.getTravelAns());
                        patientTravelHis.setText(symptomModule.getLatestTravels());
                        patientLifeChangeAns.setText(symptomModule.getChangesAns());
                        patientLifeChangeHist.setText(symptomModule.getLifeChanges());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
