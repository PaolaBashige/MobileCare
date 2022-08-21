package com.paolabora.projects.mobilecare.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.SelectService;
import com.paolabora.projects.mobilecare.Activities.PatientActivity.Services;
import com.paolabora.projects.mobilecare.R;

public class LoginActivity extends AppCompatActivity {
    private Button patientLogin, doctorLogin, forgotPassword;
    private EditText userEmail, userPassword;
    private FirebaseAuth userAuth;

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private String users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        userEmail = findViewById(R.id.patient_email_field);
        userPassword = findViewById(R.id.patient_password_field);

        patientLogin = findViewById(R.id.login_patient);
        patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users = patientLogin.getText().toString();
                loginUser();
            }
        });

        doctorLogin = findViewById(R.id.login_doctor);
        doctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users = doctorLogin.getText().toString();
                loginUser();
            }
        });

        forgotPassword = findViewById(R.id.password_forgotten_button);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPassword.class));
            }
        });
    }

    private void loginUser() {
        String usersEmail = userEmail.getText().toString().trim();
        String userPassCode = userPassword.getText().toString().trim();

        if (usersEmail.isEmpty()) {
            userEmail.setError("Please enter your Email Address");
            userEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(usersEmail).matches()) {
            userEmail.setError("Invalid Email");
            userEmail.requestFocus();
            return;
        }

        if (userPassCode.isEmpty()) {
            userPassword.setError("Please enter your Password");
            userPassword.requestFocus();
            return;
        }

        if (userPassCode.length() < 6) {
            userPassword.setError("Please provide at least 6 characters");
            userPassword.requestFocus();
            return;
        }


        userAuth.signInWithEmailAndPassword(usersEmail, userPassCode)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        if (users.equals("Patient")) {

                            checkUser("Patients", user.getUid());
                            //startActivity(new Intent(LoginActivity.this,
                                    //Services.class));
                        } else if (users.equals("Doctor")) {
                            checkUser("Doctors", user.getUid());
                            //startActivity(new Intent(LoginActivity.this,
                                //    SelectService.class));
                        }
                        userEmail.setText("");
                        userPassword.setText("");
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,
                                "Please check your email for account verification",
                                Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(LoginActivity.this,
                            "Your email or your password is incorrect",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    void checkUser(final String userType, String id){
        reference.child(userType).child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    if(userType == "Doctors"){
                        startActivity(new Intent(LoginActivity.this,
                                SelectService.class));
                    }else if(userType == "Patients"){
                        startActivity(new Intent(LoginActivity.this,
                                Services.class));
                    }else{
                        Toast.makeText(getApplication(), "Are you you person you claim to be", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplication(), "Are you you person you claim to be", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
