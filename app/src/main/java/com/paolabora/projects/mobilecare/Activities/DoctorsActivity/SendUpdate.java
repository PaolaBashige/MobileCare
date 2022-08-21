package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.paolabora.projects.mobilecare.Modules.DoctorUpdate;
import com.paolabora.projects.mobilecare.Modules.SymptomModule;
import com.paolabora.projects.mobilecare.R;

public class SendUpdate extends AppCompatActivity {
    private TextView patientSymptoms, patientSymptomsDescription, patientsName;
    private EditText diagnosis, treatment, comment;
    private Button sendUpdate, goBack;

    private DatabaseReference reference, doctorRef;
    private FirebaseUser mUser;

    private String patientId, patientSymp, patientSympDesc, patientName, sender, doctorId;
    private String doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_patient_update);

        Intent intent = getIntent();
        patientName = intent.getStringExtra("patientName");
        patientId = intent.getStringExtra("patientId");
        patientSymp = intent.getStringExtra("patientSymptoms");
        patientSympDesc = intent.getStringExtra("patientSymptomsDesc");
        doctorId = intent.getStringExtra("doctorId");

        reference = FirebaseDatabase.getInstance().getReference("Patient Ongoing History");
        doctorRef = FirebaseDatabase.getInstance().getReference("Users");
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        patientsName = findViewById(R.id.patient_name);
        patientSymptoms = findViewById(R.id.patient_symptom_list_respo);
        patientSymptomsDescription = findViewById(R.id.symptoms_brief_description_resp);
        diagnosis = findViewById(R.id.doctors_diagnosis_result);
        treatment = findViewById(R.id.patient_treatment_display);
        comment = findViewById(R.id.doc_comment_display);
        sendUpdate = findViewById(R.id.send_response_btn);
        goBack = findViewById(R.id.go_back_btn);

        patientsName.setText(patientName);
        patientSymptoms.setText(patientSymp);
        patientSymptomsDescription.setText(patientSympDesc);

        diagnosis.setText(intent.getStringExtra(""));
        diagnosis.setText(intent.getStringExtra(""));
        diagnosis.setText(intent.getStringExtra(""));

        sendUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPatientUpdate();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendUpdate.this, ViewSymptoms.class));
            }
        });
    }

    private void sendPatientUpdate() {
        final String name = patientsName.getText().toString();
        final String symptoms = patientSymptoms.getText().toString();
        final String symptomsDesc = patientSymptomsDescription.getText().toString();
        final String patientDiagnosis = diagnosis.getText().toString();
        final String patientTreatment = treatment.getText().toString();
        final String doctorsComment = comment.getText().toString();

        sender = mUser.getUid();


        doctorRef.child("Doctors").child(doctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorName = snapshot.child("doctorsFistName").getValue().toString() + " " +
                        snapshot.child("doctorsLastName").getValue().toString();

                if (patientDiagnosis.isEmpty()) {
                    diagnosis.setError("Please include a diagnosis");
                    diagnosis.requestFocus();
                }

                if (doctorsComment.isEmpty()) {
                    comment.setError("Please include a comment on the diagnosis and the treatment ");
                    comment.requestFocus();
                }

                DoctorUpdate doctorUpdate = new DoctorUpdate(name, doctorName, symptoms, symptomsDesc,
                        patientDiagnosis, patientTreatment, doctorsComment, sender, patientId);
                String uploadId = reference.push().getKey();

                reference.child("Doctor's Update").child(uploadId).setValue(doctorUpdate)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SendUpdate.this,
                                            "Your response has been uploaded",
                                            Toast.LENGTH_LONG).show();
                                    //updateSymptoms();
                                } else {
                                    Toast.makeText(SendUpdate.this,
                                            "Sorry! Your response was not uploaded",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateSymptoms() {
        Intent intent = getIntent();
        String status = "Examined";
        //String symptomId = intent.getStringExtra("symptomId");
        DatabaseReference symReference = FirebaseDatabase.getInstance()
                .getReference("Patient Ongoing History").child("Symptom(s)");

        symReference.child("symptomId").child("symptomsStatus")
                .setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}
