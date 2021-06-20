package com.example.busbooking;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Destinations extends AppCompatActivity {
    TextInputLayout til_board,til_destin;
    AutoCompleteTextView autoboard,autodestin;

    ArrayList<String> locations;
    ArrayAdapter<String> arrayAdapter_locations;

    @Override
    protected void onResume() {
        super.onResume();
        arrayAdapter_locations = new ArrayAdapter<>(getApplicationContext(),
                R.layout.dropdownitem,
                locations);
        autoboard.setAdapter(arrayAdapter_locations);



        arrayAdapter_locations = new ArrayAdapter<>(getApplicationContext(),
                R.layout.dropdownitem,
                locations);

        autodestin.setAdapter(arrayAdapter_locations);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_destinations);
        init();
        locations = new ArrayList<>();
        locations.add("Lahore");
        locations.add("Rawalpindi");
        locations.add("Peshawar");
        locations.add("Multan");
        locations.add("Hyderabad");
        locations.add("Faisalabad");
        locations.add("Quetta");
        locations.add("Sargodha");
        locations.add("Gujranwala");


    }

    private void init(){
        til_board = (TextInputLayout) findViewById(R.id.til_board);
        til_destin = (TextInputLayout) findViewById(R.id.til_destin);
        autoboard= (AutoCompleteTextView) findViewById(R.id.autonboard);
        autodestin= (AutoCompleteTextView) findViewById(R.id.autondestin);



    }

    public void iv_searchbus(View view) {
            String  from= til_board.getEditText().getText().toString();
            String  toDestin= til_destin.getEditText().getText().toString();
        //Toast.makeText(this, from, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, toDestin, Toast.LENGTH_SHORT).show();
            if(from.equals(toDestin)){
                Toast.makeText(this, "Invalid information", Toast.LENGTH_SHORT).show();
                return;
            }
           else{
        Intent intent = new Intent(Destinations.this, ticketsbuy.class);
            intent.putExtra("from",from);
            intent.putExtra("destin",toDestin);
            startActivityForResult(intent,1);}

    }
}
