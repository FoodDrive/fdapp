package com.example.dell.fddesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;


public class IndivActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText pswd,repswd;
    private EditText email;
    private Button regBtn;
    private EditText name ;
    private EditText addr;
    private EditText contact;

    String categ;
    //defining a database reference
    private DatabaseReference databaseReference;
    //

    //defining a database reference
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiv);
        categ= getIntent().getStringExtra("indiv").toString();

        //initializing firebase auth object

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //if getCurrentUser does not returns null
       /* if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }*/
        //initializing views
        name = (EditText) findViewById(R.id.name);
        addr= (EditText) findViewById(R.id.addr);
        contact = (EditText) findViewById(R.id.contact);
        email= (EditText) findViewById(R.id.email);
        pswd= (EditText) findViewById(R.id.pswd);
        repswd = (EditText) findViewById(R.id.repswd);

        //Firebase.setAndroidContext(this);

        regBtn = (Button) findViewById(R.id.regBtn);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        regBtn.setOnClickListener(this);

    }
    private void registerUser(){

        //getting email and password from edit texts
        final String Email = email.getText().toString().trim();
        final String  Pswd  =  pswd.getText().toString().trim();
        final String Name = name.getText().toString().trim();
        final String Addr  = addr.getText().toString().trim();
        final String Contact  = contact.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(Addr)){
            Toast.makeText(this,"Please enter Addr",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(Contact)){
            Toast.makeText(this,"Please enter Contact",Toast.LENGTH_LONG).show();
            return;
        }

        else if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        else if(TextUtils.isEmpty(Pswd)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(Email, Pswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            IndivData indivData= new IndivData(Name,Addr, Pswd, Contact, Email,categ);

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            databaseReference.child(user.getUid()).setValue(indivData);

                            finish();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                        else{
                            //display some message here
                            Toast.makeText(IndivActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });



    }
    @Override
    public void onClick(View view) {
        if(view == regBtn){
            registerUser();
        }
    }
}
