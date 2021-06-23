package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.busbooking.Userhelper.UsersData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class registerUser extends AppCompatActivity {
    ImageView ivBacktologin;
    TextInputLayout edRegname, edRegmobile, edRegcnic, edRegemail, edRegpassword, edRegdob;
    Button btnregister;
    RadioGroup radioGroup;

    String gender;
    RadioButton radoibutton;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String noWhiteSpace = "\\A\\w{4,20}\\z";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordVal = "^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        init();
        //register


        //back intent
        cancelbacktomain();
        //set Dob


        //

    }


    private Boolean validateName() {

        String namevalu = edRegname.getEditText().getText().toString().trim();
        if (namevalu.isEmpty()) {
            edRegname.setError("Field can't be empty");
            return false;
        } else {
            edRegname.setError(null);
            edRegname.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatemobile() {
        //String checkspaces = "Aw{1,20}z";
        String mobilevalu = edRegmobile.getEditText().getText().toString().trim();
        if (mobilevalu.isEmpty()) {
            edRegmobile.setError("Field can't be empty");
            return false;
        } else if (mobilevalu.length() < 10) {
            edRegmobile.setError("Enter Valid mobile number");
            return false;
        }  else {
            edRegmobile.setError(null);
            edRegmobile.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatecnic() {
        String cnicval = edRegcnic.getEditText().getText().toString().trim();
        if (cnicval.isEmpty()) {
            edRegcnic.setError("Field can't be empty");
            return false;
        } else if (cnicval.length() < 13) {
            edRegcnic.setError("Enter Valid CNIC");
            return false;
        } else if (!cnicval.matches(noWhiteSpace)) {
            edRegcnic.setError("Enter Valid CNIC");
            return false;
        } else {
            edRegcnic.setError(null);
            edRegname.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateemail() {
        String emailval = edRegemail.getEditText().getText().toString().trim();
        if (emailval.isEmpty()) {
            edRegemail.setError("Field can't be empty");
            return false;
        } else if (!emailval.matches(emailPattern)) {
            edRegemail.setError("Enter Valid Email Address");
            return false;
        } else {
            edRegemail.setError(null);
            edRegname.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatepassword() {
        String passval = edRegpassword.getEditText().getText().toString().trim();
        if (passval.isEmpty()) {
            edRegpassword.setError("Field can't be empty");
            return false;
        } /*else if (!passval.matches(passwordVal)) {
            edRegpassword.setError("Password is too weak");
            return false;
        }*/ else {
            edRegpassword.setError(null);
            edRegpassword.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatedob() {
        String dobval = edRegdob.getEditText().getText().toString().trim();
        if (dobval.isEmpty()) {
            edRegdob.setError("Field can't be empty");
            return false;
        } else {
            edRegdob.setError(null);
            return true;
        }

    }

    private Boolean validategender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private void cancelbacktomain() {
        ivBacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(registerUser.this, MainActivity.class);
                startActivity(login);
                finish();
            }
        });
    }


    private void init() {
        ivBacktologin = (ImageView) findViewById(R.id.ivBacktoLogin);
        edRegname = (TextInputLayout) findViewById(R.id.edRegname);
        edRegmobile = (TextInputLayout) findViewById(R.id.edRegmobile);
        edRegcnic = (TextInputLayout) findViewById(R.id.edRegcnic);
        edRegemail = (TextInputLayout) findViewById(R.id.edRegemail);
        edRegpassword = (TextInputLayout) findViewById(R.id.edRegpassword);
        edRegdob = (TextInputLayout) findViewById(R.id.edRegdob);
        // edRegaddress = (TextInputLayout) findViewById(R.id.edRegaddress);
        btnregister = (Button) findViewById(R.id.btnregister);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


    }

    public void registernew(View view) {


        //   Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();


        if (!validateemail() | !validateName() | !validatemobile() | !validatecnic() | !validategender() |
                !validatepassword() | !validatedob()) {
            //  Toast.makeText(this, edRegname.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, gender.getText().toString(), Toast.LENGTH_SHORT).show();
            return;
        }

    /*    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.male:
                         gender ="Male";
                        break;
                    case R.id.female:
                         gender = "Female";
                        break;
                    case R.id.other:
                         gender = "other";
                        break;
                }
            }
        });
        Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();*/


        String name = edRegname.getEditText().getText().toString().toUpperCase().trim();
        String mobile = edRegmobile.getEditText().getText().toString().trim();
        String cnic = edRegcnic.getEditText().getText().toString().trim();
        String email = edRegemail.getEditText().getText().toString().trim();
        String password = edRegpassword.getEditText().getText().toString();
        String dob = edRegdob.getEditText().getText().toString();

        int radiobuttonid = radioGroup.getCheckedRadioButtonId();
        radoibutton = (RadioButton) findViewById(radiobuttonid);
        //
        gender = (String) radoibutton.getText().toString();
        //Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();


        rootnode = FirebaseDatabase.getInstance(); //get root
        reference = rootnode.getReference().child("user"); //create chile(table)
        UsersData usersData = new UsersData(name, mobile, cnic, email, password, dob, gender, (double) 5000); //store in class obj

        reference.child(mobile)
                .setValue(usersData) //pass value to referece that stores child(user) reference
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        Toast.makeText(registerUser.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(registerUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        progressDialog = new ProgressDialog(registerUser.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        Intent intent = new Intent(registerUser.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void pickDateofb(View view) {
        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                registerUser.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int isAgeValid = currentYear - year;
                if (isAgeValid < 14) {
                    Toast.makeText(registerUser.this, "You are not eligible", Toast.LENGTH_SHORT).show();
                    edRegdob.getEditText().setText("");
                } else if (isAgeValid > 100) {
                    edRegdob.getEditText().setText("");
                    edRegdob.setError("Enter Valid Date");

                } else {
                    edRegdob.setError(null);
                    edRegdob.setErrorEnabled(false);
                    String date = day + " / " + month + " / " + year;
                    edRegdob.getEditText().setText(date);

                }
            }
        }, year, month, day);
        datePickerDialog.show();

    }


}