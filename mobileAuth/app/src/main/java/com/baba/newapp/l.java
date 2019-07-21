package com.baba.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class l extends AppCompatActivity {
    FirebaseAuth mAuth;
    String verificationId;
    EditText phonenumber,OTP;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l);
        btn = findViewById(R.id.btnsend);
        phonenumber = findViewById(R.id.numm);
        OTP= findViewById(R.id.otp);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = phonenumber.getText().toString().trim();
                if (number.isEmpty() || number.length() < 10) {
                    phonenumber.setError("Valid num. is reqered");
                    phonenumber.requestFocus();
                    return;
                }
                final String PhoneNumbers = "+" + "91" + number;
                sendverificationcode(PhoneNumbers);
            }

            });
    }

    public void sendverificationcode(String PhoneNumbers) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(PhoneNumbers, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,
                mCallBack);
    };

private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    @Override
    public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken forceResendingToken) {



        //storing the verification id that is sent to the user
        super.onCodeSent(s, forceResendingToken);

        //storing the verification id that is sent to the user
        verificationId = s;

    }

    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

    }

    @Override
    public void onVerificationFailed(FirebaseException e) {

    }
};
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithVredential(credential);
    }
    private void signInWithVredential(PhoneAuthCredential credential){
        final Task<AuthResult> authResultTask = mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {
                            Toast.makeText(l.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


