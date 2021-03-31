package com.example.conrep.ui.report;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conrep.R;
import com.example.conrep.database.async.report.DeleteReport;
import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.viewmodel.report.ReportViewModel;

public class ViewReport extends BaseActivity {

    private static final String TAG = "ViewReportActivity";

    private ReportEntity report;
    private ReportViewModel viewModel;
    private TextView tvName;
    private TextView tvHours;
    private TextView tvDate;
    private TextView tvSite;
    private TextView tvTasks;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_view_report, frameLayout);

        int reportID = getIntent().getIntExtra("reportID", 1);

        initiateView();

        ReportViewModel.Factory factory = new ReportViewModel.Factory(
                getApplication(), reportID);
        viewModel = ViewModelProviders.of(this, factory).get(ReportViewModel.class);
        viewModel.getReport().observe(this, reportEntity -> {
            if (reportEntity != null) {
                report = reportEntity;
                updateContent();
            }
        });
    }

    private void updateContent() {
        if (report != null) {
            setTitle("ConRep - Report " +  report.getReportID() );
            tvName.setText(report.getWorkerName());
            tvHours.setText(report.getHours() + " hours");
            tvDate.setText(report.getDate());
            tvSite.setText(report.getSiteReport()+"");
            tvTasks.setText(report.getTaskReport()+"");
            Log.i(TAG, "Activity populated.");
        }
    }

    private void initiateView() {
        tvName = findViewById(R.id.textView23);
        tvHours = findViewById(R.id.textView25);
        tvDate = findViewById(R.id.textView27);
        tvSite = findViewById(R.id.textView29);
        tvTasks = findViewById(R.id.textView31);


        Button EditBtn = findViewById(R.id.btnEditReport);
        EditBtn.setOnClickListener(view -> openEditReport());

        Button DeleteBtn = findViewById(R.id.btnDeleteReport);
        DeleteBtn.setOnClickListener(view -> openDeleteReport());

        Button BackBtn = findViewById(R.id.btnBackToReportList);
        BackBtn.setOnClickListener(view -> openBackToReportList());
    }

    private void openEditReport() {
        Intent intent = new Intent(this, EditReport.class);
        intent.putExtra("reportID", 1);
        startActivity(intent);
    }

    private void openDeleteReport() {
        generateDialog();
    }

    private void openBackToReportList() {
        Intent intent = new Intent(this, ReportList.class);
        startActivity(intent);
    }

    private void generateDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.activity_view_report, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Delete Report");
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.confirm_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast = Toast.makeText(ViewReport.this, getString(R.string.delete_report_final), Toast.LENGTH_LONG);

                toast.show();

                viewModel.deleteReport(report, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteReport: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteReport: failure", e);
                    }
                });
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel),
                (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}