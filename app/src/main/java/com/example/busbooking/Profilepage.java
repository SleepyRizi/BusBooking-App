package com.example.busbooking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Profilepage extends AppCompatActivity {
    TextView profilename,profilenumber,tvpesa;
    String username,useremail,usercnic,usermobile,namefortv;
    Double money;

     //ArrayList<Intent> capture ;
    // final  ArrayList<Intent> store= new ArrayList<>();
    ProgressDialog progressDialog;
    final int TICKET_SELL=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        init();
        setuserdata();
        tvpesa.setText(money+"0 Rs.");

    }

    @Override
    protected void onResume() {
        super.onResume();
        profilename.setText(namefortv);
        profilenumber.setText(usermobile);
        tvpesa.setText(money+"0 Rs.");

    }

    private void setuserdata() {
      /*  if(store.isEmpty()){



        capture = new ArrayList<>();
        capture.add(getIntent());

        Intent intent = getIntent();

        username= capture.get(0).getStringExtra("name");
        useremail= capture.get(0).getStringExtra("emil");
        usercnic= capture.get(0).getStringExtra("cnic");
        usermobile= capture.get(0).getStringExtra("mobile");*/
        Intent intent = getIntent();
        username= intent.getStringExtra("name");
        namefortv=username.replaceAll(" (.*)"," ");
        useremail= intent.getStringExtra("email");
        usercnic= intent.getStringExtra("cnic");
        usermobile= intent.getStringExtra("mobile");
        money= intent.getDoubleExtra("money",5000);



        profilename.setText(namefortv);
        profilenumber.setText(usermobile);
        tvpesa.setText(money+"0 Rs.");
       // store.add(capture.get(0));
        }
      /*  else{
            profilename.setText(username);
            profilenumber.setText(usermobile);
        }*/


    private void init(){
        profilename= (TextView) findViewById(R.id.profilename);
        profilenumber= (TextView) findViewById(R.id.profilephone);
        tvpesa= (TextView) findViewById(R.id.tvpesa);

    }

    public void btnlogout(View view) {
        Intent intent = new Intent(Profilepage.this,com.example.busbooking.MainActivity.class);
        startActivity(intent);
        finish();
        progressDialog = new ProgressDialog(Profilepage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );



    }




    public void profdetails(View view) {
        Intent intent = new Intent(Profilepage.this,profileDetails.class);

        intent.putExtra("name",username);
        intent.putExtra("email",useremail);
        intent.putExtra("cnic",usercnic);
        intent.putExtra("mobile",usermobile);
       // intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);

    }

    public void btnbuyticket(View view) {
        Intent intent= new Intent(Profilepage.this,Destinations.class);
        intent.putExtra("usermobile",usermobile);
        intent.putExtra("money",money);
        startActivityForResult(intent,TICKET_SELL);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TICKET_SELL && resultCode==RESULT_OK){
            Intent i= getIntent();
            money=i.getDoubleExtra("remainMoney",1500);
            tvpesa.setText(money+"0 Rs.");
        }
        else{
            Toast.makeText(this, "Process Canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
