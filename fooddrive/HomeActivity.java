package com.project.vnr.fooddrive;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button btn1,btn3,btn4;
    int status; //status bit to be taken from database
    private DatabaseReference database,myRef1;
    private FirebaseAuth firebaseAuth;
    float rating;
    RatingBar rb;
    Context context = this;
    ProfileData profileData;
    private ProgressDialog progressDialog;

    String categ; //categ - category to be taken from db - donor or collector

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Donate (or) Collect food button
        btn1 = (Button) findViewById(R.id.button1);
        btn4=(Button)findViewById(R.id.button4);
        btn3=(Button)findViewById(R.id.button3);
        //
        progressDialog = new ProgressDialog(this);

        profileData = new ProfileData();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        myRef1 = FirebaseDatabase.getInstance().getReference();
        //Getting root reference
        database = myRef1.child("Registration").child(user.getUid());
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                profileData = dataSnapshot.getValue(ProfileData.class);
                categ = profileData.Categ;
                if(categ.contentEquals("NGO")){
                    btn1.setText("COLLECT FOOD");
                } else{
                    btn1.setText("DONATE FOOD");
                }

                progressDialog.dismiss();
            }
            //}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }

        });

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startButtonAnimation1(btn1);
                    if(status==1){
                        // get prompts.xml view
                        LayoutInflater li = LayoutInflater.from(context);
                        final View promptsView = li.inflate(R.layout.activity_rating, null);

                        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        //final EditText userInput = (EditText) promptsView.findViewById(R.id.ed);

                        // set dialog message
                        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                rb = (RatingBar)promptsView.findViewById(R.id.ratingBar);
                                rating = rb.getRating();
                                status = 0;
                                //rating = (RatingBar)promptsView.findViewById(R.id.ratingBar).getRating().toString();
                                //e.setText(""+rating);
                            }
                        })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                    else{
                        if(categ.contentEquals("NGO")) {
                            //Intent intent = new Intent(getApplicationContext(),CollectActivity.class);
                            startActivity(new Intent(getApplicationContext(),CollectActivity.class));
                        } else{
                            startActivity(new Intent(getApplicationContext(),DonateActivity.class));
                        }
                    }

                }
            });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categ.equals("Individual")) {
                    Intent i = new Intent(HomeActivity.this,IndivEdit.class);
                    startActivity(i);
                } else{
                    Intent intent = new Intent(getApplicationContext(),OrgEdit.class);
                    startActivity(intent);
                }
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void startButtonAnimation1(Button btn){
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        btn.setAnimation(shake);
        btn.startAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(status==1){
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(context);
                    final View promptsView = li.inflate(R.layout.activity_rating, null);

                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    //final EditText userInput = (EditText) promptsView.findViewById(R.id.ed);

                    // set dialog message
                    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            rb = (RatingBar)promptsView.findViewById(R.id.ratingBar);
                            rating = rb.getRating();
                            status = 0;
                            //rating = (RatingBar)promptsView.findViewById(R.id.ratingBar).getRating().toString();
                            //e.setText(""+rating);
                        }
                    })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                } else{
                    if(categ.equals("ngo")) {
                        Intent intent = new Intent(getApplicationContext(), CollectActivity.class);
                        startActivity(intent);
                    }else{
                        startActivity(new Intent(getApplicationContext(),DonateActivity.class));
                    }
                }
                //fstartActivity(new Intent(getApplicationContext(), .class));
                // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.edit_profile) {
            if(categ.equals("Individual")) {
                Intent i = new Intent(HomeActivity.this, IndivEdit.class);
                startActivity(i);
            } else{
                Intent intent = new Intent(getApplicationContext(),OrgEdit.class);
                startActivity(intent);
            }

        } else if (id == R.id.faq) {
            Intent intent = new Intent(getApplicationContext(),FAQActivity.class);
            startActivity(intent);

        }else if (id == R.id.about_us) {
            Intent intent = new Intent(getApplicationContext(),AboutUs.class);
            startActivity(intent);

        }else if (id == R.id.logout) {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.ngos) {
            startActivity(new Intent(getApplicationContext(),NgoDetails.class));
        } else if (id == R.id.hotels) {
            startActivity(new Intent(getApplicationContext(),HotelDetails.class));
        } else if (id == R.id.fun_halls) {
            startActivity(new Intent(getApplicationContext(),FunHallDetails.class));
        } else if (id == R.id.indivs) {
            startActivity(new Intent(getApplicationContext(),IndivDetails.class));
        } else if (id == R.id.copr_offices) {
            startActivity(new Intent(getApplicationContext(),CorpOfficeDetails.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
