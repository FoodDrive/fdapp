package com.project.vnr.fooddrive;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DonateActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String categ;
    String uid;
Integer cnt=0;
    Spinner timeSpin;
    String time;

    Spinner qtySpin,capSpin;
    String qty,cap;

    ArrayList<String> listItems = new ArrayList<>();
    EditText e;
    Button b;
    ListView lv;
    String s="";

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //defining a database reference
    private DatabaseReference databaseReference;
    //

    //defining a database reference
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    //String result;
    private TextView result;
    final Context context = this;

    private void addItems(View view, String s) {
        listItems.add(s);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        e = (EditText) findViewById(R.id.ed);
        e.setVisibility(View.VISIBLE);
        b = (Button) findViewById(R.id.addBtn);
        lv = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);

        final String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        //SPINNER FOR TIME
        timeSpin = (Spinner) findViewById(R.id.spinTime);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.time_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinnerp
        timeSpin.setAdapter(adapter3);

        timeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //SPINNER FOR QUANTITY
        qtySpin = (Spinner) findViewById(R.id.qtySpin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.quantity_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        qtySpin.setAdapter(adapter1);

        qtySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                qty = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //SPINNER FOR TYPE
        capSpin = (Spinner) findViewById(R.id.capSpin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        capSpin.setAdapter(adapter2);

        capSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cap = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = e.getText().toString();
                if (s.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter data", Toast.LENGTH_SHORT).show();
                } else {
                    String food=s+" - "+qty+cap;
                    cnt++;
                    addItems(view,food);
                    //dbcode
                    e.setText("");
                }
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, final int position,
                                    long id) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //Toast.makeText(Main2Activity.this,"You clicked yes button",Toast.LENGTH_SHORT).show();
                                adapter.remove(adapter.getItem(position));
                                cnt--;
                                adapter.notifyDataSetChanged();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String food=" ";
                for(int i=0;i<cnt;i++){
                    //food.concat(adapter.getItem(i)+"\n");
                    food=food+adapter.getItem(i)+"\n";
                }
                firebaseAuth = FirebaseAuth.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference();

                FirebaseUser user = firebaseAuth.getCurrentUser();
                uid=user.getUid();
                FoodDetails foodDetails=new FoodDetails(uid,food,time+" hrs",formattedDate);
                databaseReference.child("FoodDetails").child(user.getUid()).setValue(foodDetails);
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
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
        getMenuInflater().inflate(R.menu.donate, menu);
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
                Intent i = new Intent(getApplicationContext(), IndivEdit.class);
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
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        } else if (id == R.id.fun_halls) {
            startActivity(new Intent(getApplicationContext(),FunHallDetails.class));
        } else if (id == R.id.indivs) {
            startActivity(new Intent(getApplicationContext(),IndivProfile.class));
        } else if (id == R.id.copr_offices) {
            startActivity(new Intent(getApplicationContext(),CorpOfficeDetails.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
