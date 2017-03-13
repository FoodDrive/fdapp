package com.project.vnr.fooddrive;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IndivEdit extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference database,myRef1;
    private FirebaseAuth firebaseAuth;

    private EditText db_name, db_addr, db_eid, db_pno;
    private ProgressDialog progressDialog;
    private Button submitBtn;
    //IndivData indivData;
    ProfileData profileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiv_edit);
        //
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        myRef1 = FirebaseDatabase.getInstance().getReference();
        //Getting root reference
        database = myRef1.child("Registration").child(user.getUid());
        progressDialog = new ProgressDialog(this);
        db_name = (EditText) findViewById(R.id.db_name);
        db_addr = (EditText) findViewById(R.id.db_addr);
        db_pno = (EditText) findViewById(R.id.db_pno);
        db_eid = (EditText) findViewById(R.id.db_eid);
        submitBtn=(Button) findViewById(R.id.submitBtn);

        //indivData = new IndivData();
        profileData = new ProfileData();

        submitBtn.setOnClickListener(this);
        showProfile();
    }
    private void showProfile(){
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                //Getting the data from snapshot
                //indivData = dataSnapshot.getValue(IndivData.class);
                profileData = dataSnapshot.getValue(ProfileData.class);

                //Displaying it on Editview
                /*db_name.setText(indivData.Name);
                db_addr.setText(indivData.Addr);
                db_pno.setText(indivData.Contact);
                db_eid.setText(indivData.Email);*/

                db_name.setText(profileData.Name);
                db_addr.setText(profileData.Addr);
                db_pno.setText(profileData.Contact);
                db_eid.setText(profileData.Email);

                progressDialog.dismiss();
            }
            //}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
                progressDialog.dismiss();
            }
        });

    }
    private void save(){
        final String Email = db_eid.getText().toString().trim();
        final String Name = db_name.getText().toString().trim();
        final String Addr  = db_addr.getText().toString().trim();
        final String Contact  = db_pno.getText().toString().trim();

        //indivData.updateData(Name,Addr, Contact, Email);
        profileData.updateData(Name,Addr, Contact, Email);

        //database.setValue(indivData);
        database.setValue(profileData);

        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
        showProfile();
    }
    @Override
    public void onClick(View v) {
        if(v==submitBtn)
            save();
    }
}
