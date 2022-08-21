package com.paolabora.projects.mobilecare.Activities.PatientActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paolabora.projects.mobilecare.Activities.PaymentActivity;
import com.paolabora.projects.mobilecare.Modules.PatientModule;
import com.paolabora.projects.mobilecare.R;

public class CreatePatientAccount extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser patient;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private static final String TAG = "CreatePatientAccount";
    private String patientId;

    private EditText mFirstName, mLastName, mMiddleName, mEmailAddress, mPassword, mConfirmPassword,
            mDateOfBirth, mRace, mGender;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient_account);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        mFirstName = findViewById(R.id.patient_first_name);
        mMiddleName = findViewById(R.id.patient_middle_name);
        mLastName = findViewById(R.id.patient_last_name);
        mEmailAddress = findViewById(R.id.patient_email_address);
        mDateOfBirth = findViewById(R.id.patient_date_of_birth);
        mRace = findViewById(R.id.patient_race);
        mGender = findViewById(R.id.patient_gender);
        mPassword = findViewById(R.id.patient_password);
        mConfirmPassword = findViewById(R.id.confirm_patient_password);

        mRegister = findViewById(R.id.create_patient_account);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        final String firstName = mFirstName.getText().toString().trim();
        final String middleName = mMiddleName.getText().toString().trim();
        final String lastName = mLastName.getText().toString().trim();
        final String emailAddress = mEmailAddress.getText().toString().trim();
        final String patientRace = mRace.getText().toString().trim();
        final String patientGender = mGender.getText().toString().trim();
        final String patientDateOfBirth = mDateOfBirth.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        if (firstName.isEmpty()) {
            mFirstName.setError("Please enter your First Name");
            mFirstName.requestFocus();
            return;
        }

        if (middleName.isEmpty()) {
            mMiddleName.setError("Please enter your Middle Name");
            mMiddleName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            mLastName.setError("Please enter your Last Name");
            mLastName.requestFocus();
            return;
        }

        if (patientGender.isEmpty()) {
            mGender.setError("Please enter your Gender");
            mGender.requestFocus();
            return;
        }

        if (patientRace.isEmpty()) {
            mRace.setError("Please enter your Race");
            mRace.requestFocus();
            return;
        }

        if (patientDateOfBirth.isEmpty()) {
            mDateOfBirth.setError("Please enter your Date of Birth");
            mDateOfBirth.requestFocus();
            return;
        }

        if (emailAddress.isEmpty()) {
            mEmailAddress.setError("Please enter your Email Address");
            mEmailAddress.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            mEmailAddress.setError("Invalid Email");
            mEmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mPassword.setError("Please enter your Password");
            mPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mPassword.setError("Please provide at least 6 characters");
            mPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            mConfirmPassword.setError("Please confirm/reenter your Password");
            mConfirmPassword.requestFocus();
            return;
        }

        if (!confirmPassword.equals(password)) {
            mConfirmPassword.setError("Your password does not match");
            mConfirmPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");

                            patient = FirebaseAuth.getInstance().getCurrentUser();
                            patientId = patient.getUid();

                            PatientModule patient = new PatientModule(firstName,
                                    middleName, lastName, emailAddress,
                                    patientDateOfBirth, patientRace, patientGender, patientId);

                                    reference.child("Patients").child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid())
                                    .setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CreatePatientAccount.this,
                                                "You have successfully registered to DigYo!",
                                                Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(
                                                CreatePatientAccount.this,
                                                PaymentActivity.class));
                                        mFirstName.setText("");
                                        mLastName.setText("");
                                        mEmailAddress.setText("");
                                        mMiddleName.setText("");
                                        mRace.setText("");
                                        mGender.setText("");
                                        mDateOfBirth.setText("");
                                        mPassword.setText("");
                                        mConfirmPassword.setText("");
                                    } else {
                                        Toast.makeText(CreatePatientAccount.this,
                                                "Your registration has failed. Try again!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreatePatientAccount.this,
                                    "Sorry! Your registration has failed.\nTry Again!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
