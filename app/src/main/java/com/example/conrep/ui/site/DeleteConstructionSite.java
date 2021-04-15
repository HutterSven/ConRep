package com.example.conrep.ui.site;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.conrep.R;
import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.viewmodel.constructionSite.ConstructionSiteViewModel;

public class DeleteConstructionSite extends BaseActivity {
    private static final String TAG = "DeleteConstructionSiteActivity";

    private ConstructionSiteEntity conSite;
    private ConstructionSiteViewModel viewModel;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvCity;
    private TextView tvOverseer;
    private TextView tvHours;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_delete_construction_site, frameLayout);

        String siteID = getIntent().getStringExtra("siteID");

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
        if (conSite != null) {
            tvName.setText(conSite.getSiteName());
            tvAddress.setText(conSite.getAddress());
            tvCity.setText(conSite.getCity());
            tvOverseer.setText(conSite.getOverseer());
            tvHours.setText(conSite.getHours()+" hours");
            Log.i(TAG, "Activity populated.");
        }
    }

    private void initiateView() {
        tvName = findViewById(R.id.tvDeleteNameSite);
        tvAddress = findViewById(R.id.tvDeleteAddressSite);
        tvCity = findViewById(R.id.tvDeleteCitySite);
        tvOverseer = findViewById(R.id.tvDeleteOverseerSite);
        tvHours = findViewById(R.id.tvDeleteHoursSite);
    }
}