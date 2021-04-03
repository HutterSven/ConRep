package com.example.conrep.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conrep.R;
import com.example.conrep.adapter.ReportRecyclerAdapter;
import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.util.RecyclerViewItemClickListener;
import com.example.conrep.ui.viewmodel.report.ReportListViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReportListSite extends BaseActivity {

    private static final String TAG = "ReportList";

    private Button btnSearchReport;
    private EditText etWorkerName;

    private List<ReportEntity> Reports;
    private ReportRecyclerAdapter reportRecyclerAdapter;
    private ReportListViewModel viewModel;

    private int siteID;

    List<ReportEntity> reportsTemp;

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;
        getLayoutInflater().inflate(R.layout.activity_report_list_site, frameLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Report List");

        siteID = getIntent().getIntExtra("siteID", 1);

        RecyclerView recyclerView = findViewById(R.id.reportRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        btnSearchReport = findViewById(R.id.btnSearchReportSite2);
        etWorkerName = findViewById(R.id.etWorkerName2);

        Reports = new ArrayList<>();
        reportsTemp = new ArrayList<>();
        reportRecyclerAdapter = new ReportRecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + reportsTemp.get(position).toString());

                Intent intent = new Intent(ReportListSite.this, ViewReport.class);
                intent.putExtra("reportID", reportsTemp.get(position).getReportID());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        ReportListViewModel.Factory factory = new ReportListViewModel.Factory(getApplication(), 0);
        viewModel = ViewModelProviders.of(this, factory).get(ReportListViewModel.class);
        viewModel.getReports().observe(this, ReportEntities -> {
            if (ReportEntities != null) {
                Reports = ReportEntities;
                    for (ReportEntity reportTemp : Reports) {
                        if (reportTemp.getSiteReport() == siteID) reportsTemp.add(reportTemp);
                    }
                    if (reportsTemp != null) reportRecyclerAdapter.setData(reportsTemp);
                }
        });


        btnSearchReport.setOnClickListener(new searchSite(this));


        recyclerView.setAdapter(reportRecyclerAdapter);
    }

    public class searchSite implements View.OnClickListener {

        ReportListSite reportList;


        public searchSite(ReportListSite reportList) {
            this.reportList = reportList;
        }

        @Override
        public void onClick(View v) {

            ReportEntity report;

            reportsTemp = new ArrayList<ReportEntity>();

            if (Reports != null) {
                for (int i = 0; i < Reports.size(); i++) {
                    report = Reports.get(i);
                    if ((report.getWorkerName().toLowerCase().contains(etWorkerName.getText().toString().toLowerCase()) || etWorkerName.getText().toString().isEmpty()) &&
                            siteID == report.getSiteReport()) {
                        reportsTemp.add(report);
                    }
                }

                if (reportsTemp != null) {
                    reportRecyclerAdapter.setData(reportsTemp);
                    reportRecyclerAdapter.notifyDataSetChanged();
                }

            }
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