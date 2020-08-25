package com.example.yashasvipamuassignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DemoGraphs extends AppCompatActivity {
ListView listView;
Button display;
DatabaseReference d;
ArrayList<String> al;
ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_graphs);
        listView=findViewById(R.id.lv);
        display=findViewById(R.id.show);
        al=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
        listView.setAdapter(adapter);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
                d= FirebaseDatabase.getInstance().getReference().child(curr.getUid()).child("Expenses");
                d.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot!=null)
                        {
                            String s="";
                            Iterable<DataSnapshot>iterable=snapshot.getChildren();
                            for(DataSnapshot ds:iterable)
                            {
                                Toast.makeText(getApplicationContext(),""+ds.getValue(),Toast.LENGTH_SHORT).show();
                                s=s+ds.getKey();
                                s=s+": ";
                                s=s+ds.getValue();
                                s=s+"\n";
                            }
                            al.add(s);
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Enter an expense",Toast.LENGTH_SHORT).show();
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