package com.example.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth = FirebaseAuth.getInstance();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user!=null){

                        startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                        //    Toast.makeText(SplashScreenActivity.this, ""+user.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {

                        Intent homeIntent = new Intent(SplashScreenActivity.this,SendOTPActivity.class);
                        startActivity(homeIntent);
                        finish();

                    }


                }
            },SPLASH_SCREEN_TIME_OUT);
        }

    }
}