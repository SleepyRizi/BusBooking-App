package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busbooking.Userhelper.UsersData;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    Button tvReg;
    TextInputLayout edMobile,edPassword;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       init();
        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,com.example.busbooking.registerUser.class);
                startActivity(i);
            }
        });

    }

    private void init(){
        tvReg= (Button) findViewById(R.id.tvReg);
        edMobile = (TextInputLayout) findViewById(R.id.edmobile);
        edPassword = (TextInputLayout) findViewById(R.id.edpassword);
      //  btnlogin=(Button) findViewById(R.id.btnlogin);
        database= FirebaseDatabase.getInstance();
    }


    private boolean emailValidate(){
        String mobile = edMobile.getEditText().getText().toString().trim();
        if(mobile.isEmpty()){
            edMobile.setError("Field can't be empty");
            return  false;
        }

        else{
            edMobile.setError(null);
            edMobile.setErrorEnabled(false);
            return true;
        }
    }
    private boolean passvalidate(){
        String passwrd= edPassword.getEditText().getText().toString();
        if(passwrd.isEmpty()){
            edPassword.setError("Field can't be empty");
            return false;
        }
        else{
            edPassword.setError(null);
            edPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void loginprocess(View view) {
        if(!passvalidate() | !emailValidate()){
            return;
        }
        else{
            isuser();

        }

    }

    private void isuser() {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );

        String userEnteredmobile = edMobile.getEditText().getText().toString().trim();
        String userEnteredpass= edPassword.getEditText().getText().toString();

        database.getReference().child("user")
                .child(userEnteredmobile) //search for mobile number entered by user
                .addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {  //if such user exists
                            progressDialog.dismiss();   //loading dialog dismiss

                            edMobile.setError(null);
                            edMobile.setErrorEnabled(false);

                            String pass = snapshot.child("password").getValue(String.class);
                            //edPassword.getEditText().setText(data);

                            if (pass.equals(userEnteredpass)) {


                                /*progressDialog = new ProgressDialog(MainActivity.this);
                                progressDialog.show();
                                progressDialog.setContentView(R.layout.progress_dialog);
                                progressDialog.getWindow().setBackgroundDrawableResource(
                                        android.R.color.transparent
                                );*/
                                String pcnic = snapshot.child("cnic").getValue(String.class);
                                String pemail = snapshot.child("email").getValue(String.class);
                                String pname = snapshot.child("name").getValue(String.class);
                                String pmobile = snapshot.child("mobile").getValue(String.class);
                                Double money = (Double) snapshot.child("money").getValue(Double.class);

                                Intent intent = new Intent(MainActivity.this, Profilepage.class);
                                intent.putExtra("name",pname);
                                intent.putExtra("email",pemail);
                                intent.putExtra("mobile",pmobile);
                                intent.putExtra("cnic",pcnic);
                                intent.putExtra("money",money);
                                startActivity(intent);
                                finish();

                            } else {
                                progressDialog.dismiss();
                                edPassword.setError("Wrong Password");
                                edPassword.requestFocus();
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            edMobile.setError("User doesn't exists");
                            edMobile.requestFocus();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


    }


}