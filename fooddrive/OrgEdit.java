package com.project.vnr.fooddrive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class OrgEdit extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference database,myRef1;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private EditText db_name, db_addr, db_eid, db_pno,db_inch_name,db_desc;
    private TextView name;
    private ProgressDialog progressDialog;
    private Button submitBtn;
    //OrgData orgData;
    ProfileData profileData;
    Button btn;

    // this is the action code we use in our intent,
    // this way we know we're looking at the response from our own action
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    private Button uploadBtn;
    private String selectedImagePath;

    ImageButton imgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_edit);
        btn=(Button)findViewById(R.id.submitBtn);
        //storageReference = FirebaseStorage.getInstance().getReference();

        imgBtn = (ImageButton)findViewById(R.id.imageBtn);
        imgBtn.setOnClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(OrgEdit.this,HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });

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
        db_inch_name = (EditText) findViewById(R.id.db_inch_name);
        db_desc = (EditText) findViewById(R.id.db_desc);
        submitBtn=(Button) findViewById(R.id.submitBtn);

        //orgData = new OrgData();
        ProfileData profileData = new ProfileData();
        storageReference = FirebaseStorage.getInstance().getReference();

        uploadBtn = (Button)findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(this);

        submitBtn.setOnClickListener(this);
        showProfile();

    }
    private void showProfile(){

        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

String uid=user.getUid();
        //retriving image
        storageReference.child("images/"+uid+".jpg").getDownloadUrl()

//Picasso.with(MainActivity.this).load(downloadURI).fit().centerCrop().into(image);
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(OrgEdit.this).load(uri).fit().centerCrop().into(imgBtn);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                //Getting the data from snapshot
                //orgData = dataSnapshot.getValue(OrgData.class);
                profileData = dataSnapshot.getValue(ProfileData.class);



                db_name.setText(profileData.Name);
                db_addr.setText(profileData.Addr);
                db_pno.setText(profileData.Contact);
                db_eid.setText(profileData.Email);
                db_inch_name.setText(profileData.InchName);
                db_desc.setText(profileData.Desc);

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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgBtn.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void uploadFile() {

        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);progressDialog.setTitle("Uploading");
            progressDialog.show();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            String uid=user.getUid();
            StorageReference riversRef = storageReference.child("images/"+uid+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }//if there is not any file
        else {
            //you can display an error toast
        }
    }

    private void save(){
        final String Email = db_eid.getText().toString().trim();
        final String Name = db_name.getText().toString().trim();
        final String Addr  = db_addr.getText().toString().trim();
        final String InchName = db_inch_name.getText().toString().trim();
        //final String Design  = db_desing.getText().toString().trim();
        final String Desc = db_desc.getText().toString().trim();
        final String Contact  = db_pno.getText().toString().trim();

        //orgData.updateData(Name,Addr,InchName, Desc, Contact, Email);
        profileData.updateData(Name,Addr,InchName, Desc, Contact, Email);

        //database.setValue(orgData);
        database.setValue(profileData);

        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
        showProfile();
    }
    @Override
    public void onClick(View v) {
        if(v==submitBtn)
            save();
        else if(v==imgBtn)
            showFileChooser();
        else if( v==uploadBtn)
            uploadFile();
        //finish();
        //startActivity(new Intent(getApplicationContext(),OrgEdit.class));
    }
}


