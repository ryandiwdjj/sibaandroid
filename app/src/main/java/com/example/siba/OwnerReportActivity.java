package com.example.siba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import API.ApiClient;


public class OwnerReportActivity extends AppCompatActivity {
    private LinearLayout pendapatan_bulanan;
    private LinearLayout pengeluaran_bulanan;
    private LinearLayout spare_terlaris;
    private LinearLayout jumlah_jasa_service;
    private LinearLayout sisa_stok_bulanan;
    private LinearLayout pendapatan_tahunan;
    private Intent i;

    ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_report);

        Toolbar myToolBar = findViewById(R.id.report_toolbar);
        myToolBar.setTitle("Laporan");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        pendapatan_bulanan = findViewById(R.id.pendapatan_btn);
        pengeluaran_bulanan = findViewById(R.id.pengeluaran_btn);
        spare_terlaris = findViewById(R.id.sparepart_terlaris_btn);
        jumlah_jasa_service =  findViewById(R.id.jumlah_jasa_service_btn);
        sisa_stok_bulanan = findViewById(R.id.sisa_stok_btn);
        pendapatan_tahunan = findViewById(R.id.pendapatan_tahunan_btn);

        i = new Intent(this, OwnerReportTampilActivity.class);

        pendapatan_bulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("tipe", "pendapatan_bulanan");
                startActivity(i);
            }
        });

        pengeluaran_bulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("tipe", "pengeluaran_bulanan");
                startActivity(i);
            }
        });

        spare_terlaris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("tipe", "spare_terlaris");
                startActivity(i);
            }
        });

        jumlah_jasa_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("tipe", "jumlah_jasa_service");
                startActivity(i);
            }
        });

        sisa_stok_bulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("tipe", "sisa_stok_bulanan");
                startActivity(i);
            }
        });

        pendapatan_tahunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("tipe", "pendapatan_tahunan");
                startActivity(i);
            }
        });

    }
}
