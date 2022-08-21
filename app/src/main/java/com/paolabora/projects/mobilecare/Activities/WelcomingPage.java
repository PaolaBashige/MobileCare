package com.paolabora.projects.mobilecare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.SelectService;
import com.paolabora.projects.mobilecare.Activities.PatientActivity.CreatePatientAccount;
import com.paolabora.projects.mobilecare.Activities.PatientActivity.Services;
import com.paolabora.projects.mobilecare.R;

public class WelcomingPage extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;

    private FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {

            Intent patientIntent = new Intent(WelcomingPage.this, Services.class);
            startActivity(patientIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcoming_page);


        btnLogin = findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPage();
            }
        });

        btnRegister = findViewById(R.id.register_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPage();
            }
        });
    }

    private void registerPage() {
        Intent intent = new Intent(this, CreatePatientAccount.class);
        startActivity(intent);
    }

    private void loginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
