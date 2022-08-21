package com.paolabora.projects.mobilecare.Activities.PatientActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.ListOfDoctors;
import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.Schedule;
import com.paolabora.projects.mobilecare.Activities.LoginActivity;
import com.paolabora.projects.mobilecare.Activities.WelcomingPage;
import com.paolabora.projects.mobilecare.R;

public class Services extends AppCompatActivity {
    private Button appointmentBtn, symptomsBtn, patientUpdateBtn, doctorsUpdateBtn, recordsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appointmentBtn = findViewById(R.id.booking_btn);
        appointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, ViewScheduleSlots.class));
            }
        });

        symptomsBtn = findViewById(R.id.symptom_btn);
        symptomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, ListOfDoctors.class));
            }
        });

        patientUpdateBtn = findViewById(R.id.update_btn);
        patientUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, Schedule.class));
            }
        });

        doctorsUpdateBtn = findViewById(R.id.view_update_btn);
        doctorsUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, DocUpdateList.class));
            }
        });

        recordsBtn = findViewById(R.id.report_btn);
        recordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, ViewMedicalHistory.class));
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Services.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Services.this, WelcomingPage.class));
                finish();
                return true;
            case R.id.delete_account:
                deleteConfirmation();
                return true;
        }
        return false;
    }

    private void deleteConfirmation() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Deleting DigYo Account");
        alertDialog.setMessage("Do you really want to delete your account?");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount();
                    }
                });
        alertDialog.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

    private void deleteAccount() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Services.this, "Your account has been deleted",
                            Toast.LENGTH_LONG);
                    startActivity(new Intent(Services.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(Services.this, "Your account was not deleted",
                            Toast.LENGTH_LONG);
                }
            }
        });

    }
}
