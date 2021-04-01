package com.example.conrep.ui.site;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.conrep.R;
import com.example.conrep.database.site.ConstructionSiteEntity;
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

        int siteID = getIntent().getIntExtra("siteID", 1);

        initiateView();

        ConstructionSiteViewModel.Factory factory = new ConstructionSiteViewModel.Factory(
                getApplication(), siteID);
        viewModel = ViewModelProviders.of(this, factory).get(ConstructionSiteViewModel.class);
        viewModel.getConstructionSite().observe(this, constructionSiteEntity -> {
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
        tvName = findViewById(R.id.textView17);
        tvAddress = findViewById(R.id.textView18);
        tvCity = findViewById(R.id.textView19);
        tvOverseer = findViewById(R.id.textView15);
        tvHours = findViewById(R.id.textView16);
    }
}