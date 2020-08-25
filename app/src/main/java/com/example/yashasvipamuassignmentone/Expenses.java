package com.example.yashasvipamuassignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Expenses extends AppCompatActivity {
EditText expname,expense;
Button add,graphs;
public DatabaseReference d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        expname=findViewById(R.id.en);
        expense=findViewById(R.id.enm);
        add=findViewById(R.id.adde);
        graphs=findViewById(R.id.demo);
        d= FirebaseDatabase.getInstance().getReference();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expn,expm;
                expn=expname.getText().toString();
                expm=expense.getText().toString();
                int expamount=Integer.parseInt(expm);
                if(expamount!=0)
                {
                    FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
                    d.child(curr.getUid()).child("Expenses").child(expn).setValue(expm);
                    Toast.makeText(getApplicationContext(),"Expense added",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter value greater than 0",Toast.LENGTH_SHORT).show();
                }

            }
        });
        graphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Expenses.this,DemoGraphs.class));
            }
        });
    }
}