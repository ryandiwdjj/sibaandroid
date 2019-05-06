package com.example.siba;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import API.ApiInterface;
import Fragments.PenjualanTampilFragment;
import Fragments.SupplierTambahFragment;
import Fragments.SupplierTampilFragment;
import Models.penjualan;
import Recycler.RecyclerAdapterPenjualan;
import Recycler.RecyclerAdapterSparepartHargaJual;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanActivity extends AppCompatActivity {
    //fragment declaration
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        Toolbar myToolBar = findViewById(R.id.penjualan_toolbar);
        myToolBar.setTitle("Tampil Penjualan");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        fragmentManager.beginTransaction().replace(R.id.frame_penjualan, new PenjualanTampilFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.penjualan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toolbar myToolBar = findViewById(R.id.penjualan_toolbar);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.tambah_penjualan_tb:
                myToolBar.setTitle("Tambah Penjualan");
//                fragmentManager.beginTransaction().replace(R.id.frame_supplier, new SupplierTambahFragment()).commit();
//                Toast.makeText(this, "Tambah success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tampil_penjualan_tb:
                myToolBar.setTitle("Tampil Penjualan");
                fragmentManager.beginTransaction().replace(R.id.frame_penjualan, new PenjualanTampilFragment()).commit();
//                Toast.makeText(this, "Tampil success", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return true;
    }
}
