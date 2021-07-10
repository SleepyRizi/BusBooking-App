package com.example.busbooking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busbooking.Userhelper.DBuser;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileDetails extends AppCompatActivity {
    TextInputLayout tvname,tvemail,tvcnic,tvmobile;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String noWhiteSpace = "\\A\\w{4,20}\\z";
    DatabaseReference reference;
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
    private Boolean validateName() {

        String namevalu = tvname.getEditText().getText().toString().trim();
        if (namevalu.isEmpty()) {
            tvname.setError("Field can't be empty");
            return false;
        } else {
            tvname.setError(null);
            tvname.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatecnic() {
        String cnicval = tvcnic.getEditText().getText().toString().trim();
        if (cnicval.isEmpty()) {
            tvcnic.setError("Field can't be empty");
            return false;
        } else if (cnicval.length() < 13) {
            tvcnic.setError("Enter Valid CNIC");
            return false;
        } else if (!cnicval.matches(noWhiteSpace)) {
            tvcnic.setError("Enter Valid CNIC");
            return false;
        } else {
            tvcnic.setError(null);
            tvcnic.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateemail() {
        String emailval = tvemail.getEditText().getText().toString().trim();
        if (emailval.isEmpty()) {
            tvemail.setError("Field can't be empty");
            return false;
        } else if (!emailval.matches(emailPattern)) {
            tvemail.setError("Enter Valid Email Address");
            return false;
        } else {
            tvemail.setError(null);
            tvemail.setErrorEnabled(false);
            return true;
        }
    }


    private void init(){
            tvname= (TextInputLayout) findViewById(R.id.dialogname);
            tvemail= (TextInputLayout) findViewById(R.id.dialogemail);
            tvcnic= (TextInputLayout) findViewById(R.id.dialogcnic);
            tvmobile= (TextInputLayout) findViewById(R.id.dialogmobile);
            reference=FirebaseDatabase.getInstance().getReference().child("user");



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


    public void btn_update(View view) {
        if (!validateemail() | !validateName() | !validatecnic()) {

            return;
        }
        String newName= tvname.getEditText().getText().toString();
        String newCNIC= tvcnic.getEditText().getText().toString();
        String newEmail= tvemail.getEditText().getText().toString();
        String mobile=tvmobile.getEditText().getText().toString();
        //sql
        DBuser dBuser= new DBuser(profileDetails.this);
        dBuser.open();

        dBuser.updateRecord(mobile,newName,newEmail,newCNIC);

        dBuser.close();
        //sql update end

        //firebase
        reference.child(mobile).child("name").setValue(newName);
        reference.child(mobile).child("cnic").setValue(newCNIC);
        reference.child(mobile).child("email").setValue(newEmail);


        //firebase update end
        finish();
        //FirebaseDatabase.getInstance().getReference().child(mobile).

    }

}