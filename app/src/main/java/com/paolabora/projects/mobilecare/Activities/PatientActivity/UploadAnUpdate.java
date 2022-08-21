package com.paolabora.projects.mobilecare.Activities.PatientActivity;

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
import com.paolabora.projects.mobilecare.Modules.PatientModule;
import com.paolabora.projects.mobilecare.Modules.PatientUpdate;
import com.paolabora.projects.mobilecare.R;

public class UploadAnUpdate extends AppCompatActivity {
    private TextView docDiagnosis, docTreatmentSuggestion, docComments, docName;
    private EditText patientComment;
    private Button sendUpdate;
    private String doctorName, receiver, doctorId;

    private DatabaseReference reference, userReference;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    public String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_an_update);

        docName = findViewById(R.id.doctor_name);

        Intent intent = getIntent();
        receiver = intent.getStringExtra("doctorId");
        docName.setText(intent.getStringExtra("doctorName"));

        reference = FirebaseDatabase.getInstance().getReference("Patient Ongoing History");
        userReference = FirebaseDatabase.getInstance().getReference("Users");

        docDiagnosis = findViewById(R.id.update_diagnosis_display);
        docTreatmentSuggestion = findViewById(R.id.update_treatmt_disp);
        docComments = findViewById(R.id.update_comment_disp);
        patientComment = findViewById(R.id.your_update_result);

        docDiagnosis.setText(intent.getStringExtra("patientDiagnosis"));
        docTreatmentSuggestion.setText(intent.getStringExtra("patientTreatment"));
        docComments.setText(intent.getStringExtra("doctorComment"));

        sendUpdate = findViewById(R.id.send_update_btn);
        sendUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendingUpdate();
            }
        });
    }

    private void sendingUpdate() {
        final String doctorsDiagnosis = docDiagnosis.getText().toString();
        final String docTreatmentSug = docTreatmentSuggestion.getText().toString();
        final String doctorsComment = docComments.getText().toString();
        final String patientComments = patientComment.getText().toString();
        final String sender = mUser.getUid();


        userReference.child("Patients").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientName = snapshot.child("patientFirstName").getValue().toString() + " " +
                        snapshot.child("patientMiddleName").getValue().toString()+ " " +
                        snapshot.child("patientLastName").getValue().toString();

               /* userReference.child("Doctors").child(receiver).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            DoctorModule doctorModule = snapshot1.getValue(DoctorModule.class);
                            docName.setText(doctorModule.getDoctorsFistName() + " " + doctorModule.getDoctorsLastName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

                doctorName = docName.getText().toString();

                PatientUpdate update = new PatientUpdate(patientName, doctorName, doctorsDiagnosis, docTreatmentSug,
                        doctorsComment, patientComments, sender, receiver);
                String uploadId = reference.push().getKey();

                reference.child("Updates From Patient").child(uploadId).setValue(update)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Toast.makeText(UploadAnUpdate.this,
                                            "Your update has been uploaded",
                                            Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(UploadAnUpdate.this,
                                            "Sorry! Your update has NOT been uploaded",
                                            Toast.LENGTH_LONG).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
