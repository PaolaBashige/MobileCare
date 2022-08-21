package com.paolabora.projects.mobilecare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paolabora.projects.mobilecare.Modules.PaymentModule;
import com.paolabora.projects.mobilecare.R;

public class PaymentActivity extends AppCompatActivity {
    private CardForm cardForm;
    private Button subscribeBtn;
    private FirebaseUser mUser;
    private DatabaseReference reference;
    private String subscriber, cardNumber, expirationYear, cvv, postalCde,
            countryCode, mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Payments Information");

        cardForm = findViewById(R.id.card_form);
        subscribeBtn = findViewById(R.id.subscribe_btn);

        //.cardholderName(CardForm.FIELD_REQUIRED)

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(PaymentActivity.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {

                    AlertDialog.Builder alertBuilder =
                            new AlertDialog.Builder(PaymentActivity.this);
                    alertBuilder.setTitle("Please confirm your subscription");

                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " +
                            cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());

                    alertBuilder.setPositiveButton("Confirm Subscription",
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            subscribing();
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();

                    alertDialog.show();

                }else {
                    Toast.makeText(PaymentActivity.this,
                            "Please, make sure all the field are filled",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void subscribing() {
        subscriber = mUser.getUid();
        cardNumber = cardForm.getCardNumber();
        expirationYear = cardForm.getExpirationYear();
        cvv = cardForm.getCvv();
        postalCde = cardForm.getPostalCode();
        countryCode = cardForm.getCountryCode();
        mobileNumber = cardForm.getMobileNumber();

        PaymentModule module = new PaymentModule(subscriber, cardNumber, expirationYear, cvv,
                postalCde, countryCode, mobileNumber);
        String uploadKey = reference.push().getKey();

        reference.child("Payments").child(uploadKey).setValue(module).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this,
                            "Thank you for subscribing to DigYo",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PaymentActivity.this,
                            "Sorry! Your subscription failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
