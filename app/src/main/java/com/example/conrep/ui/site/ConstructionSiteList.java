package com.example.conrep.ui.site;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.conrep.R;
import com.example.conrep.adapter.ConstructionSiteRecyclerAdapter;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.util.RecyclerViewItemClickListener;
import com.example.conrep.ui.viewmodel.constructionSite.ConstructionSiteListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ConstructionSiteList extends BaseActivity {

    private static final String TAG = "SiteList";

    private Button btnSearchSite;
    private EditText etSiteID;
    private EditText etName;
    private EditText etAddress;

    private List<ConstructionSiteEntity> ConstructionSites;
    private ConstructionSiteRecyclerAdapter constructionSiteRecyclerAdapter;
    private ConstructionSiteListViewModel viewModel;

    private int siteID;

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;
        getLayoutInflater().inflate(R.layout.activity_construction_site_list, frameLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Construction Site List");

        RecyclerView recyclerView = findViewById(R.id.siteRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        btnSearchSite = findViewById(R.id.btnSearchSite);
        etName = findViewById(R.id.etSearchSiteName);
        etAddress = findViewById(R.id.etSearchSiteName);

        ConstructionSites = new ArrayList<>();
        constructionSiteRecyclerAdapter = new ConstructionSiteRecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + ConstructionSites.get(position).toString());

                Intent intent = new Intent(ConstructionSiteList.this, ViewConstructionSite.class);
                intent.putExtra("siteID", ConstructionSites.get(position).getSiteID());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        ConstructionSiteListViewModel.Factory factory = new ConstructionSiteListViewModel.Factory(getApplication(), 0);
        viewModel = ViewModelProviders.of(this, factory).get(ConstructionSiteListViewModel.class);
        viewModel.getSites().observe(this, ConstructionSiteEntities -> {
            if (ConstructionSiteEntities != null) {
                ConstructionSites = ConstructionSiteEntities;
                System.out.println(ConstructionSites.get(0).getSiteName());
                constructionSiteRecyclerAdapter.setData(ConstructionSites);
            }
        });


        btnSearchSite.setOnClickListener(new ConstructionSiteList.searchSite(this));


        recyclerView.setAdapter(constructionSiteRecyclerAdapter);
    }

    public class searchSite implements View.OnClickListener {

        ConstructionSiteList constructionSiteList;

        public searchSite(ConstructionSiteList constructionSiteList) {
            this.constructionSiteList = constructionSiteList;
        }

        @Override
        public void onClick(View v) {

            ConstructionSiteEntity conSite;

            List<ConstructionSiteEntity> constructionSitesTemp = new ArrayList<ConstructionSiteEntity>();

            if (etSiteID.getText().toString().isEmpty() && etSiteID.getText().toString().isEmpty()) {
                constructionSiteRecyclerAdapter.setData(ConstructionSites);
                constructionSiteRecyclerAdapter.notifyDataSetChanged();
                return;
            }

            for (int i = 0; i < ConstructionSites.size(); i++) {
                conSite = ConstructionSites.get(i);
                if (conSite.getSiteName().toLowerCase().contains(etName.getText().toString().toLowerCase()) &&
                        (Integer.parseInt(etSiteID.getText().toString()) == conSite.getSiteID() || etSiteID.getText().toString().isEmpty())) {
                    constructionSitesTemp.add(conSite);
                }
            }

            constructionSiteRecyclerAdapter.setData(constructionSitesTemp);
            constructionSiteRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}