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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paolabora.projects.mobilecare.Modules.DoctorResponseToUpdate;
import com.paolabora.projects.mobilecare.R;

public class RespondToUpdate extends AppCompatActivity {
    private TextView patientName, patientDiagnosis, patientTreatment, patientComment;
    private EditText doctorComment;
    private Button sendResponse;
    private DatabaseReference reference;
    private FirebaseUser mUser;
    private String fUser, patientId, patientsName, patientsDiagnosis, patientsTreatment, patientsComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond_to_update);

        reference = FirebaseDatabase.getInstance().getReference("Patient Ongoing History")
                .child("Doctor Response to Patient Update");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        fUser = mUser.getUid();

        Intent intent = getIntent();

        patientName = findViewById(R.id.patient_name);
        patientDiagnosis = findViewById(R.id.update_diagnosis_display);
        patientTreatment = findViewById(R.id.update_treatmt_disp);
        patientComment = findViewById(R.id.patient_comment);
        doctorComment = findViewById(R.id.update_comment_disp);

        patientsName = intent.getStringExtra("patientName");
        patientsDiagnosis = intent.getStringExtra("patientDiagnosis");
        patientsTreatment = intent.getStringExtra("patientTreatment");
        patientsComment = intent.getStringExtra("patientUpdate");

        patientName.setText(patientsName);
        patientDiagnosis.setText(patientsDiagnosis);
        patientTreatment.setText(patientsTreatment);
        patientComment.setText(patientsComment);

        patientId = intent.getStringExtra("patientId");

        sendResponse = findViewById(R.id.send_update_btn);
        sendResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendYourResponse();
            }
        });
    }

    private void sendYourResponse() {
        String doctorsResponse = doctorComment.getText().toString();

        DoctorResponseToUpdate update = new DoctorResponseToUpdate(fUser, patientId,
                patientsDiagnosis, patientsTreatment, patientsComment, doctorsResponse);

        reference.child(reference.push().getKey()).setValue(update)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RespondToUpdate.this,
                            "Your response has been uploaded",
                            Toast.LENGTH_LONG).show();
                    //updateSymptoms();
                } else {
                    Toast.makeText(RespondToUpdate.this,
                            "Sorry! Your response was not uploaded",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
