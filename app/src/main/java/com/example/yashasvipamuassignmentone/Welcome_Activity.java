package com.example.yashasvipamuassignmentone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome_Activity extends AppCompatActivity {
EditText name,phone,regid;
Button save,logout,home;
public DatabaseReference d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);
        d = FirebaseDatabase.getInstance().getReference();
        name=findViewById(R.id.edit0);
        phone=findViewById(R.id.edit1);
        regid=findViewById(R.id.edit2);
        save=findViewById(R.id.but1);
        home=findViewById(R.id.home);
        logout=findViewById(R.id.lout);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();


                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
                Date date = new Date(year-1900, month, day);
                String strDate = dateFormatter.format(date);

                String Name,Phone, Regid;
                Name=name.getText().toString();
                Phone=phone.getText().toString();
                Regid=regid.getText().toString();

                if(Phone.length()<10 || Phone.length()>10 || Phone.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter a valid Phone number",Toast.LENGTH_LONG).show();
                }
                else {
                    UserAcc ua = new UserAcc(Name, Phone, Regid, strDate);
                    FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
                    d.child(curr.getUid()).setValue(ua);
                    Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome_Activity.this,Home.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getApplicationContext()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Welcome_Activity.this, SignUp.class));
                        finish();
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(Welcome_Activity.this,SignUp.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}