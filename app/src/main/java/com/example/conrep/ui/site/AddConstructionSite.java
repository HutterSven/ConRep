package com.example.conrep.ui.site;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.conrep.R;
import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.viewmodel.constructionSite.ConstructionSiteViewModel;

public class AddConstructionSite extends BaseActivity {

    private Button addSiteBtn;

    private static final String TAG = "AddConstructionSite";

    private ConstructionSiteEntity conSite;
    private ConstructionSiteViewModel viewModel;

    private EditText etSiteName;
    private EditText etSiteCity;
    private EditText etSiteAddress;
    private EditText etSiteOverseer;

    public AddConstructionSite() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_construction_site, frameLayout);

        initiateView();

    }

    private void initiateView() {
        etSiteName = findViewById(R.id.etSiteName);
        etSiteCity = findViewById(R.id.etSiteCity);
        etSiteAddress = findViewById(R.id.etSiteAddress);
        etSiteOverseer = findViewById(R.id.etSiteOverseer);

        addSiteBtn = findViewById(R.id.AddSiteSite);

        ConstructionSiteViewModel.Factory factory = new ConstructionSiteViewModel.Factory(
                getApplication(), "");

        viewModel = ViewModelProviders.of(this, factory).get(ConstructionSiteViewModel.class);

        addSiteBtn.setOnClickListener(v -> {

            if (etSiteName.getText().toString().trim().length() > 0 && etSiteCity.getText().toString().trim().length() > 0 && etSiteAddress.getText().toString().trim().length() > 0 && etSiteOverseer.getText().toString().trim().length() > 0
                    && etSiteOverseer.getText().toString().trim().contains("@")) {
                addSite();
            } else {
                etSiteName.requestFocus();
                etSiteName.setError("Enter Name");
                etSiteCity.requestFocus();
                etSiteCity.setError("Enter City");
                etSiteAddress.requestFocus();
                etSiteAddress.setError("Enter Address");
                etSiteOverseer.requestFocus();
                etSiteOverseer.setError("Enter valid E-Mail");
                Toast.makeText(getApplicationContext(),"Enter all fields and fill in a valid E-Mail in Overseer",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addSite() {

        conSite = new ConstructionSiteEntity();
        conSite.setSiteName(etSiteName.getText().toString());
        conSite.setCity(etSiteCity.getText().toString());
        conSite.setAddress(etSiteAddress.getText().toString());
        conSite.setOverseer(etSiteOverseer.getText().toString());
        conSite.setHours(0);

        viewModel.createConstructionSite(conSite, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                System.out.println("Site created");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Site not created");
            }
        });

        Intent intent = new Intent(this, ConstructionSiteList.class);
        startActivity(intent);
    }
}