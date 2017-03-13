package com.project.vnr.fooddrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class NgoDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String categ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.ngo_details, menu);
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
            startActivity(new Intent(getApplicationContext(),CorpOfficeDetails.class));;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
