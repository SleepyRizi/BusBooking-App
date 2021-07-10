package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busbooking.Userhelper.DBuser;
import com.google.firebase.FirebaseError;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTickets extends AppCompatActivity {


    String mobile;
    DatabaseReference reference;
    DatabaseReference refbuses;
    public boolean flag=true;

    //set texts
    TextView fromlocation;
    TextView starttime;
    TextView destination;
    TextView arrivetime;
    TextView ticketprice;
    TextView tvstarttime;
    TextView tvbustype;
    CardView mycardview;
    public ArrayList<BusModelclass> busShow;

    //--------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        init();
        if(flag){
            mycardview.setVisibility(View.VISIBLE);
        }else{
            mycardview.setVisibility(View.INVISIBLE);
        }

        busShow= new ArrayList<>();
        try {
            showTicket();
        } catch (FirebaseException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        if(flag){
            mycardview.setVisibility(View.VISIBLE);
        }else{
            mycardview.setVisibility(View.INVISIBLE);
        }





    }
    public void showTicket() throws FirebaseException{

        //-------------------------------------

        Intent intent=getIntent();
        mobile=intent.getStringExtra("mobile");
        reference= FirebaseDatabase.getInstance().getReference().child("/user").child(mobile).child("ticket");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String myticket=snapshot.child("Ticket").getValue(String.class);
                //-----------------------
                busShow.clear();

                refbuses= FirebaseDatabase.getInstance().getReference().child("/busses").child(myticket);
                refbuses.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            BusModelclass bus = snapshot.getValue(BusModelclass.class);
                            //Toast.makeText(MyTickets.this, snapshot.toString(), Toast.LENGTH_SHORT).show();
                            busShow.add(bus);
                            if(busShow.isEmpty()){
                                flag=false; //if empty
                            }else{
                                flag=true; //not empty

                            }

                        }

                        fromlocation.setText(busShow.get(0).getFromlocation());
                        starttime.setText(busShow.get(0).getStarttiming());
                        destination.setText(busShow.get(0).getArrivelocation());
                        arrivetime.setText(busShow.get(0).getArrivetiming());
                        ticketprice.setText(busShow.get(0).getPrice());
                        tvstarttime.setText(busShow.get(0).getStarttiming());
                        tvbustype.setText(busShow.get(0).getBusCondition());



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //-------------------------------------

        /*refbuses= FirebaseDatabase.getInstance().getReference().child("/busses").child(myticket.toString());


        refbuses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    busShow.clear();
                    Toast.makeText(MyTickets.this, snapshot.toString(), Toast.LENGTH_SHORT).show();
                    BusModelclass bus = snap.getValue(BusModelclass.class);
                    busShow.add(bus);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

                           // Toast.makeText(MyTickets.this, "Moved", Toast.LENGTH_SHORT).show();
        //     Toast.makeText(MyTickets.this, busShow.get(0).getFromlocation().toString(), Toast.LENGTH_SHORT).show();
        //   Toast.makeText(MyTickets.this, busShow.get(0).getArrivelocation().toString(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(MyTickets.this, busShow.get(0).getStarttiming().toString(), Toast.LENGTH_SHORT).show();




    }

    public void init(){


        //initialize

        fromlocation=(TextView) findViewById(R.id.c_tvlocationticket);
        starttime=(TextView) findViewById(R.id.c_tvmovtime);
        destination=(TextView) findViewById(R.id.c_tvdestinticket);
        arrivetime=(TextView) findViewById(R.id.c_tvarrivetime);
        ticketprice=(TextView) findViewById(R.id.c_tvticketprice);
        tvstarttime=(TextView) findViewById(R.id.c_tvshowTime);
        tvbustype=(TextView) findViewById(R.id.c_tv_bustype);
        mycardview=(CardView)findViewById(R.id.mycardView);
/*
        fromlocation.setText(busShow.get(0).getFromlocation());
        starttime.setText(busShow.get(0).getStarttiming());
        destination.setText(busShow.get(0).getArrivelocation());
        arrivetime.setText(busShow.get(0).getArrivetiming());
        ticketprice.setText(busShow.get(0).getPrice());
        tvstarttime.setText(busShow.get(0).getStarttiming());
        tvbustype.setText(busShow.get(0).getBusCondition());


*/



    }

    public void btnCancelTicket(View view) {

        DBuser dBuser = new DBuser(MyTickets.this);
        dBuser.open();
        dBuser.deleteTicket(mobile);
        dBuser.close();
        FirebaseDatabase.getInstance().getReference().child("/user").child(mobile).child("ticket").setValue(null);
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        finish();


    }
}