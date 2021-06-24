package com.example.mobilestore.scanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilestore.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class StartScannerActivity extends AppCompatActivity {
    //initialize variable
    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_scanner);

        //assign variable
        btScan = findViewById(R.id.bt_scan);

        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize intent integrator
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        StartScannerActivity.this
                );
                //set prompt text
                intentIntegrator.setPrompt("For flash use volume up key");
                //set beep
                intentIntegrator.setBeepEnabled(true);
                //locked orientation
                intentIntegrator.setOrientationLocked(true);
                //set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                //initiate scan
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //initiate intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        //check condition
        if(intentResult.getContents() != null){
            //when result content is not null
            //initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    StartScannerActivity.this
            );
            //set title
            builder.setTitle("Product's information:");
            //set message
            builder.setMessage(intentResult.getContents());
            //set positive button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            //show alert dialog
            builder.show();
        }
        else{
            //when result content is null
            //display toast
            Toast.makeText(getApplicationContext(),"Sorry! You did not scan any thing", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
