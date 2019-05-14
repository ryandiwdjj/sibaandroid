package com.example.siba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import API.ApiClient;


public class OwnerReportActivity extends AppCompatActivity {
    private WebView report;
    ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_report);

        report = findViewById(R.id.report_view);
        report.setWebViewClient(new WebViewClient());
        report.loadUrl(apiClient.getUrl() + "laporan/pendapatan_bulanan");
//        report.loadUrl("http://192.168.1.6:8000/laporan/pendapatan_bulanan");

        WebSettings webSettings = report.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
