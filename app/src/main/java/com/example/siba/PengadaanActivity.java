package com.example.siba;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import Fragments.PengadaanTambahFragment;
import Fragments.PengadaanTampilFragment;

public class PengadaanActivity extends AppCompatActivity {
    //fragment declaration
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengadaan);

        Toolbar myToolBar = findViewById(R.id.pengadaan_toolbar);
        myToolBar.setTitle("Tampil Pengadaan");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        fragmentManager.beginTransaction().replace(R.id.frame_pengadaan, new PengadaanTampilFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pengadaan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toolbar myToolBar = findViewById(R.id.pengadaan_toolbar);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.tambah_pengadaan_tb:
                myToolBar.setTitle("Tambah Pengadaan");
                fragmentManager.beginTransaction().replace(R.id.frame_pengadaan, new PengadaanTambahFragment()).commit();
//                Toast.makeText(this, "Tambah success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tampil_pengadaan_tb:
                myToolBar.setTitle("Tampil Pengadaan");
                fragmentManager.beginTransaction().replace(R.id.frame_pengadaan, new PengadaanTampilFragment()).commit();
//                Toast.makeText(this, "Tampil success", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return true;
    }
}
