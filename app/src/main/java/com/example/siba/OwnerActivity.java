package com.example.siba;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class OwnerActivity extends AppCompatActivity {

    LinearLayout supplier_button;
    LinearLayout sparepart_button;
    LinearLayout pengadaan_button;
    LinearLayout penjualan_button;
    LinearLayout laporan_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        Toolbar myToolBar = findViewById(R.id.owner_toolbar);
        myToolBar.setTitle("Owner");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        supplier_button = findViewById(R.id.supplier_btn);
        sparepart_button = findViewById(R.id.sparepart_btn);
        pengadaan_button = findViewById(R.id.pengadaan_btn);
        penjualan_button = findViewById(R.id.penjualan_btn);
        laporan_button = findViewById(R.id.laporan_btn);

        //SUPPLIER IMAGE BUTTON
        supplier_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent suplier_i = new Intent(OwnerActivity.this, OwnerSupplierActivity.class);
                startActivity(suplier_i);
            }
        });

        //SPAREPART IMAGE BUTTON
        sparepart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sparepart_i = new Intent(OwnerActivity.this, OwnerSparepartActivity.class);
                startActivity(sparepart_i);
            }
        });

        //PENGADAAN IMAGE BUTTON
        pengadaan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sparepart_i = new Intent(OwnerActivity.this, PengadaanActivity.class);
                startActivity(sparepart_i);
            }
        });

        //PENJUALAN IMAGE BUTTON
        penjualan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent penjualan_i = new Intent(OwnerActivity.this, PenjualanActivity.class);
                startActivity(penjualan_i);
            }
        });

        //LAPORAN IMAGE BUTTON
        laporan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OwnerActivity.this, "Laporan pressed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.owner_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_owner:
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Anda yakin ingin logout?");

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //log out function
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("login_cred", null);
                        ed.putString("login_role", null);
                    }
                });
                break;
        }
        return true;
    }
}
