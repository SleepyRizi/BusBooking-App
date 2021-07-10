package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.busbooking.Userhelper.DBuser;
import com.example.busbooking.Userhelper.TicketAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ticketsbuy extends AppCompatActivity implements TicketAdapter.itemSelected{

    RecyclerView recyclerView;
    RecyclerView.Adapter ticketAdapter;
    //TicketAdapter ticketAdapter;
//    DatabaseReference dbref;
    private ArrayList<BusModelclass> listData;
    private ArrayList<BusModelclass> templistData;
    String usermobile;
    Double money;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketsbuy);
        //getting intent
        listData=new ArrayList<>();
        templistData=new ArrayList<>();
        Intent intent= getIntent();
        String from= intent.getStringExtra("from");
        String to= intent.getStringExtra("destin");
         usermobile= intent.getStringExtra("usermobile");
        money = intent.getDoubleExtra("money",5000);


        recyclerView=findViewById(R.id.ticket_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseDatabase.getInstance().getReference("busses")
                .orderByChild("fromlocation")
                .equalTo(from)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        listData.clear();
                        if(snapshot.exists()){
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                BusModelclass bus = snap.getValue(BusModelclass.class);
                                if(bus.arrivelocation.equals(to)){

                                    listData.add(bus);
                                }
                            }
                             /*   for(int i=0;i<listData.size();i++){
                                    if(listData.get(i).arrivelocation.equals("to")){
                                        templistData.add(listData.get(i));
                                    }
                                    ticketAdapter.notifyDataSetChanged();*/

                                //Toast.makeText(ticketsbuy.this, listData.get(0).getBusCondition(), Toast.LENGTH_SHORT).show();
                            }
                           ticketAdapter.notifyDataSetChanged();

                    /*    FirebaseDatabase.getInstance().getReference()
                                .child("Busses")
                                .orderByChild("arrivelocation")
                                .equalTo(to)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                        listData.clear();
                                        if(snapshot.exists()) {
                                            for (DataSnapshot snap : snapshot.getChildren()) {
                                                BusModelclass bus = snap.getValue(BusModelclass.class);
                                                //if(bus.fromlocation.equals("from")) {
                                                    listData.add(bus);
                                                //}

                                            }
                                            ticketAdapter.notifyDataSetChanged();
                                        }
                                    }



                                    @Override
                                    public void onCancelled(@NonNull  DatabaseError error) {
                                        Toast.makeText(ticketsbuy.this, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ticketsbuy.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        ticketAdapter= new TicketAdapter(this, listData);
        recyclerView.setAdapter(ticketAdapter);


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listData.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        BusModelclass bus = dataSnapshot.getValue(BusModelclass.class);
                        listData.add(bus);
                    }
                    ticketAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };



    }



    @Override
    public void onClickball(int index) {
        // FirebaseDatabase.getInstance().getReference().child("busses").orderByChild("")
       // Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show();
        //
        String busid = listData.get(index).getBusid();
        String ticketprice = listData.get(index).getPrice();
        StringBuffer ticketPrice = new StringBuffer(ticketprice);
        //ticketPrice.delete(4,6);//5000

        int price = Integer.parseInt((String) ticketPrice.subSequence(0, 4));
      //  Toast.makeText(this, ticketPrice.subSequence(0, 4), Toast.LENGTH_SHORT).show();

      //  Toast.makeText(this, busid, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(ticketsbuy.this,Profilepage.class);
//        intent.putExtra("busid",busid);
//        setResult(RESULT_OK);
//        finish();
       // Toast.makeText(this, usermobile, Toast.LENGTH_SHORT).show();

        if (money >= price){
            money-=price;
            HashMap<String, Object> map = new HashMap<>();
        map.put("Ticket", busid);
        FirebaseDatabase.getInstance().getReference().child("user").child(usermobile)
                .child("ticket").setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent= new Intent(ticketsbuy.this,Destinations.class);
                        intent.putExtra("remainMoney",money);
                        setResult(RESULT_OK);
                        Toast.makeText(ticketsbuy.this, "Successful purchased", Toast.LENGTH_SHORT).show();
                        finish();
                        //Toast.makeText(ticketsbuy.this, "Successful purchased", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ticketsbuy.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
          //sql write
            DBuser dBuser = new DBuser(ticketsbuy.this);
            dBuser.open();
            dBuser.addTicket(usermobile,busid);
            dBuser.close();
          //
        //Toast.makeText(this, money.toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, ((toString(money))), Toast.LENGTH_SHORT).show();


    }else{
            Toast.makeText(this, "Not Enough money", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
        }


}