package com.example.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otpactivity);

        //init
        final EditText inputMobile = findViewById(R.id.inputMobile);
        final Button buttonGetOTP = findViewById(R.id.buttonGetOTP);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        buttonGetOTP.setOnClickListener(v -> {
            //toast error
            if(inputMobile.getText().toString().isEmpty() || inputMobile.getText().toString().replace(" ","").length()!=10){
                Toast.makeText(SendOTPActivity.this, "Enter valid mobile", Toast.LENGTH_SHORT).show();
                return;
            }
            //set visibility

            //verify phone number


            new AlertDialog.Builder(SendOTPActivity.this)
                    .setTitle("Confirmation")
                    .setMessage("We will Send OTP Code On this +91 "+inputMobile.getText().toString()+" Is It Correct")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Correct Send it!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            buttonGetOTP.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);

                            PhoneAuthOptions options =
                                    PhoneAuthOptions.newBuilder()
                                            .setPhoneNumber("+91"+inputMobile.getText().toString())
                                            .setTimeout(60L, TimeUnit.SECONDS)
                                            .setActivity(SendOTPActivity.this)
                                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    progressBar.setVisibility(View.GONE);
                                                    buttonGetOTP.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                    progressBar.setVisibility(View.GONE);
                                                    buttonGetOTP.setVisibility(View.VISIBLE);
                                                    Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                    progressBar.setVisibility(View.GONE);
                                                    buttonGetOTP.setVisibility(View.VISIBLE);
                                                    //action
                                                    Intent intent = new Intent(getApplicationContext(),VerifyOTPActivity.class);
                                                    intent.putExtra("mobile",inputMobile.getText().toString());
                                                    intent.putExtra("verificationId",verificationId);
                                                    startActivity(intent);
                                                }
                                            })
                                            .build();
                            PhoneAuthProvider.verifyPhoneNumber(options);

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();



        });
    }

}