package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.busbooking.Userhelper.TicketAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ticketsbuy extends AppCompatActivity {

    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketsbuy);
        recyclerView=findViewById(R.id.ticket_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //fetching data from firebase
        DatabaseReference query= FirebaseDatabase.getInstance().getReference().child("busses");
        FirebaseRecyclerOptions<BusModelclass> options=
                new FirebaseRecyclerOptions.Builder<BusModelclass>()
                .setQuery(query, BusModelclass.class)
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