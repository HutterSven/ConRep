package com.example.conrep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button addSite, settings, about;

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

//        settings = findViewById(R.id.settings);
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openSettings();
//            }
//        });
//
//        about = findViewById(R.id.about);
//        about.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openAbout();
//            }
//        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void openAddSite() {
        Intent intent = new Intent(this, AddConstructionSite.class);
        startActivity(intent);
    }

    private void openSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private void openAbout() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }


}