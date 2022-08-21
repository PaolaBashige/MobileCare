package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

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
import com.paolabora.projects.mobilecare.Activities.UpdateLists;
import com.paolabora.projects.mobilecare.Modules.PatientUpdate;
import com.paolabora.projects.mobilecare.R;

public class ViewPatientUpdate extends AppCompatActivity {
    private TextView patientName, patientDiagnosis, patientTreatment,
            patientComment;
    private Button giveUpdate;
    private String patient = "hello", patientId, doctorId;

    private DatabaseReference userRef, patientUpdateRef;
    private FirebaseUser mUser;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_update);

        patientName = findViewById(R.id.patient_name);
        patientDiagnosis = findViewById(R.id.patient_diagnosis_display);
        patientTreatment = findViewById(R.id.patient_treatment_disp);
        patientComment = findViewById(R.id.patient_update_on_result);

        giveUpdate = findViewById(R.id.send_update_btn);

        userRef = FirebaseDatabase.getInstance().getReference("Users");
        patientUpdateRef = FirebaseDatabase.getInstance().getReference("Patient Ongoing History")
                .child("Updates From Patient");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        doctorId = mUser.getUid();

        giveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent = new Intent(ViewPatientUpdate.this, SendUpdate.class);
                //startActivity(intent);
                passIntent();
            }
        });

        patientUpdateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot3 : snapshot.getChildren()) {
                    patientId = snapshot3.child("senderId").getValue(String.class);
                }

                Log.d("Patient Id: ", patientId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent i = getIntent();
        patient = i.getStringExtra("patientName");

        patientUpdateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot4 : snapshot.getChildren()) {
                    PatientUpdate patientUpdate = snapshot4.getValue(PatientUpdate.class);
                    if (doctorId.equals(patientUpdate.getReceiverId()) ||
                            doctorId.equals(patientUpdate.getSenderId())) {
                        patientName.setText(patient);

                        patientDiagnosis.setText(patientUpdate.getDocDiagnosis());
                        patientTreatment.setText(patientUpdate.getDocTreatmentSuggestion());
                        patientComment.setText(patientUpdate.getPatientComment());
                    }

                    if (doctorId.equals(patientUpdate.getReceiverId())) {
                        giveUpdate.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void passIntent() {
        intent = new Intent(ViewPatientUpdate.this, RespondToUpdate.class);
        intent.putExtra("patientName", patientName.getText().toString());
        intent.putExtra("patientTreatment", patientTreatment.getText().toString());
        intent.putExtra("patientUpdate", patientComment.getText().toString());
        intent.putExtra("patientDiagnosis", patientDiagnosis.getText().toString());
        intent.putExtra("patientId", patientId);

        startActivity(intent);
    }
}
