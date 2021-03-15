package com.example.conrep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button addSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSite = findViewById(R.id.button8);
        addSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddSite();
            }



        });
    }


    private void openAddSite() {
        Intent intent = new Intent(this, AddConstructionSite.class);
        startActivity(intent);
    }


}