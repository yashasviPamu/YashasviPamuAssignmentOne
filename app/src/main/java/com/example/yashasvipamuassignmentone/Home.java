package com.example.yashasvipamuassignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
EditText budget;
Button add,exp,balance;
TextView checkBal;
int total=0,sub=0,expense=0;
String str;
public DatabaseReference d,d1,d2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        budget=findViewById(R.id.ed1);
        add=findViewById(R.id.addb);
        exp=findViewById(R.id.e);
        balance=findViewById(R.id.bal);
        checkBal=findViewById(R.id.chkbal);
        d= FirebaseDatabase.getInstance().getReference();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Budget=budget.getText().toString();
                int bud= Integer.parseInt(Budget);
                if(bud!=0)
                {
                    FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
                    d.child(curr.getUid()).child("Budget").child("Week's Budget").setValue(Budget);
                    Toast.makeText(getApplicationContext(),"Budget Added",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter a value greater than 0",Toast.LENGTH_SHORT).show();
                }
            }
        });
        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Expenses.class));
            }
        });
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
                d1 = FirebaseDatabase.getInstance().getReference().child(curr.getUid());
                d1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot != null) {
                            for (DataSnapshot ds : snapshot.child("Budget").getChildren()) {
                                String amount = ds.getValue(String.class);
                                Toast.makeText(getApplicationContext(), amount, Toast.LENGTH_SHORT).show();
                                sub += Integer.valueOf(amount);
                            }
                            for (DataSnapshot ds : snapshot.child("Expenses").getChildren()) {
                                String amount = ds.getValue(String.class);
                                Toast.makeText(getApplicationContext(), amount, Toast.LENGTH_SHORT).show();
                                expense += Integer.valueOf(amount);
                            }
                            total = sub - expense;
                            str = "" + total;
                            checkBal.setText(str);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });
    }
}