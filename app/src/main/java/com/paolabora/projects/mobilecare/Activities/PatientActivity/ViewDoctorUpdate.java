package com.paolabora.projects.mobilecare.Activities.PatientActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Modules.DoctorUpdate;
import com.paolabora.projects.mobilecare.R;

public class ViewDoctorUpdate extends AppCompatActivity {
    private TextView doctorName, patientSymptoms, symptomDescription, patientDiagnosis, patientTreatment,
            doctorComment;
    private Button giveUpdate, goBack;
    private String docName = "hello", patientId, doctorId;

    private DatabaseReference userRef, docUpdateRef;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_updates);

        doctorName = findViewById(R.id.doctor_name);
        patientSymptoms = findViewById(R.id.patient_symptom_list_respo);
        symptomDescription = findViewById(R.id.symptoms_brief_description_resp);
        patientDiagnosis = findViewById(R.id.diagnosis_result);
        patientTreatment = findViewById(R.id.treatment_display);
        doctorComment = findViewById(R.id.comment_display);

        giveUpdate = findViewById(R.id.send_update_btn);

        userRef = FirebaseDatabase.getInstance().getReference("Users");
        docUpdateRef = FirebaseDatabase.getInstance().getReference("Patient Ongoing History")
                .child("Doctor's Update");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        patientId = mUser.getUid();

        docUpdateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot3 : snapshot.getChildren()) {
                    doctorId = snapshot3.child("senderId").getValue(String.class);
                }

                Log.d("Doctor Id: ", doctorId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        giveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAnUpdate();
            }
        });

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
        final Query userQuery = rootRef.orderByChild(mUser.getUid());



        Intent i = getIntent();
        docName = i.getStringExtra("doctorName");

        docUpdateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot4 : snapshot.getChildren()) {
                    DoctorUpdate doctorUpdate = snapshot4.getValue(DoctorUpdate.class);
                    if (patientId.equals(doctorUpdate.getReceiverId()) ||
                            patientId.equals(doctorUpdate.getSenderId())) {
                        doctorName.setText(docName);
                        patientSymptoms.setText(doctorUpdate.getPatientSymptoms());
                        symptomDescription.setText(doctorUpdate.getSymptomDescription());
                        patientDiagnosis.setText(doctorUpdate.getDiagnosis());
                        patientTreatment.setText(doctorUpdate.getTreatment());
                        doctorComment.setText(doctorUpdate.getComment());
                    }

                    if (patientId.equals(doctorUpdate.getReceiverId())) {
                        giveUpdate.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendAnUpdate() {
        Intent intent = new Intent(ViewDoctorUpdate.this, UploadAnUpdate.class);
        intent.putExtra("doctorId", doctorId);
        intent.putExtra("doctorName", doctorName.getText());
        intent.putExtra("patientDiagnosis", patientDiagnosis.getText().toString());
        intent.putExtra("patientTreatment", patientTreatment.getText().toString());
        intent.putExtra("doctorComment", doctorComment.getText().toString());
        startActivity(intent);
    }
}
