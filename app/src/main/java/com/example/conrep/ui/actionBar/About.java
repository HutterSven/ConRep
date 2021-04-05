package com.example.conrep.ui.actionBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.conrep.R;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.MainActivity;

public class About extends BaseActivity {

    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_about, frameLayout);

        initiateView();

    }

    private void initiateView() {
        backBtn = findViewById(R.id.btnBackAbout);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void back() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}