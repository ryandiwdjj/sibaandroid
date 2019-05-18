package com.example.siba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import API.ApiClient;

public class OwnerReportTampilActivity extends AppCompatActivity {
    private WebView report;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_report_tampil);

        WebSettings webSettings;

        Intent i = getIntent();

        report = findViewById(R.id.report_web);

        report.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        switch (i.getStringExtra("tipe")) {
            case ("pendapatan_bulanan"):
                report.setWebViewClient(new WebViewClient());
                report.loadUrl(apiClient.getUrl() + "laporan/pendapatan_bulanan");
//        report.loadUrl("http://192.168.1.6:8000/laporan/pendapatan_bulanan");

                 webSettings = report.getSettings();
                webSettings.setJavaScriptEnabled(true);

                break;
            case ("pengeluaran_bulanan"):
                report.setWebViewClient(new WebViewClient());
                report.loadUrl(apiClient.getUrl() + "laporan/pengeluaran_bulanan");
//        report.loadUrl("http://192.168.1.6:8000/laporan/pendapatan_bulanan");

                webSettings = report.getSettings();
                webSettings.setJavaScriptEnabled(true);

                break;
            case ("spare_terlaris"):
                report.setWebViewClient(new WebViewClient());
                report.loadUrl(apiClient.getUrl() + "laporan/sparepart_terlaris");
//        report.loadUrl("http://192.168.1.6:8000/laporan/pendapatan_bulanan");

                webSettings = report.getSettings();
                webSettings.setJavaScriptEnabled(true);

                break;
            case ("jumlah_jasa_service"):
                report.setWebViewClient(new WebViewClient());
                report.loadUrl(apiClient.getUrl() + "laporan/jasa_service");
//        report.loadUrl("http://192.168.1.6:8000/laporan/pendapatan_bulanan");

                webSettings = report.getSettings();
                webSettings.setJavaScriptEnabled(true);

                break;
            case ("sisa_stok_bulanan"):
                report.setWebViewClient(new WebViewClient());
                report.loadUrl(apiClient.getUrl() + "laporan/sisa_stok_bulanan");
//        report.loadUrl("http://192.168.1.6:8000/laporan/pendapatan_bulanan");

                webSettings = report.getSettings();
                webSettings.setJavaScriptEnabled(true);

                break;
            case ("pendapatan_tahunan"):
                report.setWebViewClient(new WebViewClient());
                report.loadUrl(apiClient.getUrl() + "laporan/pendapatan_tahunan");
//        report.loadUrl("http://192.168.1.6:8000/laporan/pendapatan_bulanan");

                webSettings = report.getSettings();
                webSettings.setJavaScriptEnabled(true);

                break;

        }
    }
}
