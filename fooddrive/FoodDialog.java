package com.project.vnr.fooddrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FoodDialog extends AppCompatActivity {

    String uid;
    Button acceptBtn;
    Button backBtn;
    TextView db_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dialog);

        //uid = getIntent().getStringExtra("uid").toString();

        /*acceptBtn = (Button)findViewById(R.id.acceptBtn);
        backBtn = (Button)findViewById(R.id.backBtn);*/

        db_name = (TextView)findViewById(R.id.db_name);
        db_name.setText(uid);

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete from database
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CollectActivity.class));
            }
        });
    }
}
