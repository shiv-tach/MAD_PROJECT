package com.example.solo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Profile extends AppCompatActivity {

    TextView text1,text2;
    Button btn,btn2;
    DatabaseReference dataRef;
    Users person;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        text1 = findViewById(R.id.rr1);
        text2 = findViewById(R.id.rr2);
        btn = findViewById(R.id.button2);
        btn2 = findViewById(R.id.btn4);

        person = new Users();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataRef = FirebaseDatabase.getInstance().getReference().child("User/162");
                dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChildren()){

                            text1.setText(dataSnapshot.child("fullName").getValue().toString());
                            text2.setText(dataSnapshot.child("name").getValue().toString());



                        }
                        else{

                            Toast.makeText(getApplicationContext(),"Cannot find Table",Toast.LENGTH_SHORT).show();
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {



                    }
                });




            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getApplicationContext(),User.class);
                startActivity(intent);


            }
        });



    }
}