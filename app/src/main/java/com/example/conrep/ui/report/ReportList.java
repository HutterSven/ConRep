package com.example.conrep.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
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

public class ReportList extends BaseActivity {

    private static final String TAG = "ReportList";

    private Button btnSearchReport;
    private EditText etSiteID;
    private EditText etWorkerName;

    private List<ReportEntity> Reports;
    private ReportRecyclerAdapter reportRecyclerAdapter;
    private ReportListViewModel viewModel;

    private int siteID;

    private Bundle savedInstanceState;

    private List<ReportEntity> reportsTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;
        getLayoutInflater().inflate(R.layout.activity_report_list, frameLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Report List");

        RecyclerView recyclerView = findViewById(R.id.reportRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        btnSearchReport = findViewById(R.id.btnSearchReportSite);
        etSiteID = findViewById(R.id.etSearchReportBySite);
        etWorkerName = findViewById(R.id.etWorkerName);

        Reports = new ArrayList<>();
        reportsTemp = new ArrayList<>();

        reportRecyclerAdapter = new ReportRecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                if (reportsTemp.size() > 0) Log.d(TAG, "clicked on: " + reportsTemp.get(position).toString());

                Intent intent = new Intent(ReportList.this, ViewReport.class);
                if (reportsTemp.size() > 0) intent.putExtra("reportID", reportsTemp.get(position).getReportID());
                else  intent.putExtra("reportID", Reports.get(position).getReportID());
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
                System.out.println(Reports.get(0).getWorkerName());
                reportRecyclerAdapter.setData(Reports);
            }
        });

        for (ReportEntity reportTemp : Reports) {
            reportsTemp.add(reportTemp);
        }


        btnSearchReport.setOnClickListener(new searchSite(this));


        recyclerView.setAdapter(reportRecyclerAdapter);
    }

    public class searchSite implements View.OnClickListener {

        ReportList reportList;

        public searchSite(ReportList reportList) {
            this.reportList = reportList;
        }

        @Override
        public void onClick(View v) {

            ReportEntity report;

            reportsTemp = new ArrayList<ReportEntity>();

            if (etSiteID.getText().toString().isEmpty() && etSiteID.getText().toString().isEmpty()) {
                reportRecyclerAdapter.setData(Reports);
                reportRecyclerAdapter.notifyDataSetChanged();
                return;
            }

            for (int i = 0; i < Reports.size(); i++) {
                report = Reports.get(i);
                if (report.getWorkerName().toLowerCase().contains(etWorkerName.getText().toString().toLowerCase()) &&
                        (Integer.parseInt(etSiteID.getText().toString()) == report.getSiteReport() || etSiteID.getText().toString().isEmpty())) {
                    reportsTemp.add(report);
                }
            }

            reportRecyclerAdapter.setData(reportsTemp);
            reportRecyclerAdapter.notifyDataSetChanged();
        }
    }

}