package com.example.solo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateData extends AppCompatActivity {


    DatabaseReference dataRef;
    TextView text1,text2;
    EditText edit1,edit2;
    Button btn2,btn1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        text1 = findViewById(R.id.textView2);
        text2 = findViewById(R.id.textView3);
        edit2 = findViewById(R.id.edittext2);
        btn1 = findViewById(R.id.button2);
        btn2 = findViewById(R.id.button4);
        Intent intent = getIntent();

        final String username = intent.getStringExtra("user");


        Toast.makeText(getApplicationContext(),""+username,Toast.LENGTH_SHORT).show();




        dataRef = FirebaseDatabase.getInstance().getReference().child("User/"+username);
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

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataRef = FirebaseDatabase.getInstance().getReference().child("User/"+username);

                dataRef.child("fullName").setValue(edit2.getText().toString());

                text1.setText(edit2.getText().toString());



            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Working",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),User_Profile.class);
                intent.putExtra("user",username);
                startActivity(intent);



            }
        });



    }
}