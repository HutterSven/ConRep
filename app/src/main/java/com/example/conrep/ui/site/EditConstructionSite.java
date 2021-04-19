package com.example.conrep.ui.site;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conrep.R;
import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.viewmodel.constructionSite.ConstructionSiteViewModel;

public class EditConstructionSite extends BaseActivity {

    private static final String TAG = "EditConstructionSite";

    private ConstructionSiteEntity conSite;
    private ConstructionSiteViewModel viewModel;

    private EditText etSiteName;
    private EditText etSiteCity;
    private EditText etSiteAddress;
    private EditText etSiteOverseer;

    String siteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_construction_site, frameLayout);

        siteID = getIntent().getStringExtra("siteID");

        initiateView();

        ConstructionSiteViewModel.Factory factory = new ConstructionSiteViewModel.Factory(
                getApplication(), siteID);
        viewModel = ViewModelProviders.of(this, factory).get(ConstructionSiteViewModel.class);
        viewModel.getConstructionSite(siteID).observe(this, constructionSiteEntity -> {
            if (constructionSiteEntity != null) {
                conSite = constructionSiteEntity;
                updateContent();
            }
        });

    }

    private void updateContent() {
        etSiteName.setText(conSite.getSiteName());
        etSiteCity.setText(conSite.getCity());
        etSiteAddress.setText(conSite.getAddress());
        etSiteOverseer.setText(conSite.getOverseer());
    }

    private void initiateView() {

        etSiteName = findViewById(R.id.etSiteNameE);
        etSiteCity = findViewById(R.id.etSiteCityE);
        etSiteAddress = findViewById(R.id.etSiteAddressE);
        etSiteOverseer = findViewById(R.id.etSiteOverseerE);

        Button fileReportBtn = findViewById(R.id.btnSaveEditSite);

        fileReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((etSiteOverseer.getText().toString().trim().contains("@") || etSiteOverseer.getText().toString().isEmpty())) {
                    addSite();
                } else {
                    etSiteOverseer.setError("Enter valid E-Mail");
                    Toast.makeText(getApplicationContext(),"Enter a valid E-Mail in Overseer",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addSite() {

        conSite = new ConstructionSiteEntity();
        conSite.setSiteName(etSiteName.getText().toString());
        conSite.setCity(etSiteCity.getText().toString());
        conSite.setAddress(etSiteAddress.getText().toString());
        conSite.setOverseer(etSiteOverseer.getText().toString());
        conSite.setHours(conSite.getHours());
        conSite.setSiteID(siteID);

        viewModel.updateConstructionSite(conSite, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateSite: success");
            }
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateSite: failure", e);
            }
        });

        Intent intent = new Intent(this, ViewConstructionSite.class);
        intent.putExtra("siteID", conSite.getSiteID());
        startActivity(intent);
    }

}