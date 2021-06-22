package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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

public class ticketsbuy extends AppCompatActivity {

    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;
    DatabaseReference dbref;
    private List<BusModelclass>listData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketsbuy);
        //getting intent
        listData=new ArrayList<>();
        Intent intent= getIntent();
        String from= intent.getStringExtra("from");
        String to= intent.getStringExtra("destin");


      // ArrayList<DataSnapshot> busModelclasses= new ArrayList<>();
        recyclerView=findViewById(R.id.ticket_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query=FirebaseDatabase.getInstance().getReference().child("busses");
       // dbref= FirebaseDatabase.getInstance().getReference().child("busses");
       // dbref.orderByChild("busid").addValueEventListener(new ValueEventListener() {
         /*   @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for( DataSnapshot buses: snapshot.getChildren() ){
                        if (buses.child("fromlocation").equals(from) && buses.child("arrivelocation").equals(to)){
                            BusModelclass list= buses.getValue(BusModelclass.class);
                            listData.add(list);
                        }

                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //fetching data from firebase
     /*   DatabaseReference query= (DatabaseReference) FirebaseDatabase.getInstance().getReference()
                 .child("busses").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {

                        for( DataSnapshot buses: snapshot.getChildren() ){

                            if (buses.child("fromlocation").equals(from) && buses.child("arrivelocation").equals(to)){

                               // busModelclasses.add(buses);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });;*/

        FirebaseRecyclerOptions<BusModelclass> options=
                new FirebaseRecyclerOptions.Builder<BusModelclass>()
                .setQuery(query,BusModelclass.class)
                .build();
         ticketAdapter= new TicketAdapter(options);
        recyclerView.setAdapter(ticketAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        ticketAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ticketAdapter.stopListening();
    }
}