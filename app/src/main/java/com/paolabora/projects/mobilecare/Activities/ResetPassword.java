package com.paolabora.projects.mobilecare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.paolabora.projects.mobilecare.R;

public class ResetPassword extends AppCompatActivity {

    private EditText emailReset;
    private Button emailResetBtn;
    private FirebaseAuth mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailReset = findViewById(R.id.reset_password);
        emailResetBtn = findViewById(R.id.reset_pass);

        mUser = FirebaseAuth.getInstance();

        emailResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailResetStr = emailReset.getText().toString();

                if (emailResetStr.isEmpty()){
                    emailReset.setError("Please fill this field");
                    emailReset.requestFocus();
                } else {
                    mUser.sendPasswordResetEmail(emailResetStr).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassword.this, "Please check your email",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassword.this, LoginActivity.class));
                            } else {
                                Toast.makeText(ResetPassword.this, "Password Resetting Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
