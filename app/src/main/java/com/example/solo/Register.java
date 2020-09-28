package com.example.solo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


        std = new Users();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("User");

                try{
                    if(TextUtils.isEmpty(name.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Name is Empty",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(fme.getText().toString())){

                        Toast.makeText(getApplicationContext(),"Full Name is Empty",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(password.getText().toString())){

                        Toast.makeText(getApplicationContext(),"Password is Empty",Toast.LENGTH_SHORT).show();

                    }
                    else{

                        std.setName(name.getText().toString().trim());
                        std.setFullName(fme.getText().toString().trim());
                        std.setPassword(password.getText().toString().trim());
                        dbRef.child("std2").setValue(std);
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