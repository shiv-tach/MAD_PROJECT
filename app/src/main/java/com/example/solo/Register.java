package com.example.solo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PublicKey;

public class Register extends AppCompatActivity {

    EditText name, fme,password;
    Button btn;
    Users std;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        name = findViewById(R.id.userInput);
        fme = findViewById(R.id.fname);
        password = findViewById(R.id.pass);
        btn = findViewById(R.id.button6);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                std = new Users();

                dbRef = FirebaseDatabase.getInstance().getReference().child("User");


                String uname = name.getText().toString();
                String fullName = fme.getText().toString();
                String nPassword = password.getText().toString();

                try{
                    if(uname.isEmpty()){

                        name.setError("Enter User Name");
                        name.requestFocus();
                    }
                    else if(fullName.isEmpty()){

                        fme.setError("Enter Full Name");
                        fme.requestFocus();

                    }
                    else if(nPassword.isEmpty()){

                        password.setError("Enter password");
                        password.requestFocus();

                    }
                    else{

                        std.setName(name.getText().toString().trim());
                        std.setFullName(fme.getText().toString().trim());
                        std.setPassword(password.getText().toString().trim());

                        dbRef.child(""+name.getText().toString()).setValue(std);

                        Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_SHORT).show();
                        clearControls();
                        Intent intent =new Intent(getApplicationContext(),User.class);
                        startActivity(intent);

                    }



                }catch (NumberFormatException nfe){

                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                }

            }
        });







    }

    private void clearControls(){

        name.setText("");
        fme.setText("");
        password.setText("");
    }
}