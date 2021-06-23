package com.example.busbooking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class profileDetails extends AppCompatActivity {
    TextInputLayout tvname,tvemail,tvcnic,tvmobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        init();
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        else {
            Intent intent = getIntent();
            tvname.getEditText().setText(intent.getStringExtra("name"));
            tvemail.getEditText().setText(intent.getStringExtra("email"));
            tvcnic.getEditText().setText(intent.getStringExtra("cnic"));
            tvmobile.getEditText().setText(intent.getStringExtra("mobile"));

        }



    }
    private void init(){
            tvname= (TextInputLayout) findViewById(R.id.dialogname);
            tvemail= (TextInputLayout) findViewById(R.id.dialogemail);
            tvcnic= (TextInputLayout) findViewById(R.id.dialogcnic);
            tvmobile= (TextInputLayout) findViewById(R.id.dialogmobile);


    }

    public void backtoprofilepage(View view) {
        Intent intent = new Intent(profileDetails.this,Profilepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(1);
        // startActivity(intent);
        finish();
       /* intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();*/


    }


}