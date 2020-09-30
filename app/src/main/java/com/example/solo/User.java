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
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class User extends AppCompatActivity {

    DatabaseReference dbRef;
    EditText fme;
    String names;
    int countValue= Register.counts;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button button,button2;
        final EditText text1, txt2;

        text1 = findViewById(R.id.UID);
        txt2 = findViewById(R.id.PSD);
        button = findViewById(R.id.btn1);

        Toast.makeText(getApplicationContext(),""+countValue,Toast.LENGTH_SHORT).show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name;
                name = text1.getText().toString();

                String Upassword = txt2.getText().toString();
                String userName = text1.getText().toString();

                for (int i = 113; i <= countValue; i++) {

                    dbRef = FirebaseDatabase.getInstance().getReference().child("User/id"+i);
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            names = dataSnapshot.child("name").getValue().toString();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if (userName.isEmpty()) {

                        text1.setError("Insert User Name");
                        text1.requestFocus();

                    } else if (Upassword.isEmpty()) {

                        txt2.setError("Enter Password");
                        txt2.requestFocus();
                    } else if (name.equals(names)) {


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        //Toast.makeText(getApplicationContext(), "Simple Button 2", Toast.LENGTH_LONG).show();

                    } else {


                       Toast.makeText(getApplicationContext(), "else part of loop"+i + names, Toast.LENGTH_SHORT).show();
                    }

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