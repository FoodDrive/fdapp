package com.project.vnr.fooddrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegActivity extends AppCompatActivity {

    Button restBtn = null;
    Button funHallBtn = null;
    Button corpOfficeBtn = null;
    Button ngoBtn = null;
    Button indivBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        restBtn = (Button)findViewById(R.id.btnRes);
        restBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrgActivity.class);
                intent.putExtra("category",restBtn.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });

        funHallBtn = (Button)findViewById(R.id.btnFunc);
        funHallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrgActivity.class);
                intent.putExtra("category",funHallBtn.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });

        corpOfficeBtn = (Button)findViewById(R.id.btnCorp);
        corpOfficeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrgActivity.class);
                intent.putExtra("category",corpOfficeBtn.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });

        ngoBtn = (Button)findViewById(R.id.btnNgo);
        ngoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrgActivity.class);
                intent.putExtra("category",ngoBtn.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });

        indivBtn = (Button)findViewById(R.id.btnIndiv);
        indivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),IndivActivity.class);
                intent.putExtra("indiv",indivBtn.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        
    }

}
