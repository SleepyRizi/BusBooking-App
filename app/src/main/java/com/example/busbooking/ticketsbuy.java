package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.busbooking.Userhelper.TicketAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ticketsbuy extends AppCompatActivity implements TicketAdapter.itemSelected{

    RecyclerView recyclerView;
    RecyclerView.Adapter ticketAdapter;
    //TicketAdapter ticketAdapter;
    DatabaseReference dbref;
    private ArrayList<BusModelclass> listData;
    private ArrayList<BusModelclass> templistData;



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
        Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show();
    }
}