package com.example.solo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class User extends AppCompatActivity {

    DatabaseReference dbRef;
    String names,pas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button button,button2;
        final EditText text1, txt2;

        text1 = findViewById(R.id.UID);
        txt2 = findViewById(R.id.PSD);
        button = findViewById(R.id.btn1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name;


                name = text1.getText().toString();
                final String Upassword = txt2.getText().toString();
                final String userName = text1.getText().toString();

                if (userName.isEmpty()) {

                    text1.setError("Insert User Name");
                    text1.requestFocus();

                } else if (Upassword.isEmpty()) {

                    txt2.setError("Enter Password");
                    txt2.requestFocus();
                } else {


                    dbRef = FirebaseDatabase.getInstance().getReference().child("User/" + userName);
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                if(dataSnapshot.getValue() != null){

                                    names = dataSnapshot.child("name").getValue().toString();


                                    pas = dataSnapshot.child("password").getValue().toString();


                                    if (name.equals(names) && Upassword.equals(pas)) {


                                        Intent intent = new Intent(getApplicationContext(), User_Profile.class);
                                        intent.putExtra("userName", name);
                                        startActivity(intent);

                                    } else {


                                        Toast.makeText(getApplicationContext(), "Password Wrong", Toast.LENGTH_SHORT).show();

                                    }


                                }
                                else{

                                    Toast.makeText(getApplicationContext(),"You have to Register",Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });



                }
            }

        });



        button2 = findViewById(R.id.btn2);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 =new Intent(getApplicationContext(),Register.class);
                startActivity(intent2);


            }
        });





    }



}