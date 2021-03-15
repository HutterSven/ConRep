package com.example.conrep.site;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conrep.R;

public class AddConstructionSite extends AppCompatActivity {

    private Button addSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_construction_site);

        addSite = findViewById(R.id.button8);
        addSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo add variable name
                openAddSite(null);
            }
        });
    }

    private void openAddSite(String name) {
        Intent intent = new Intent(this, ViewConstructionSite.class);
        intent.putExtra("siteName", name );
        startActivity(intent);
    }
}