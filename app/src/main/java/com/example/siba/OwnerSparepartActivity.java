package com.example.siba;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import Fragments.SparepartTambahFragment;
import Fragments.SparepartTampilFragment;

public class OwnerSparepartActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_sparepart);

        Toolbar myToolBar = findViewById(R.id.sparepart_toolbar);
        myToolBar.setTitle("Tampil Sparepart");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        fragmentManager.beginTransaction().replace(R.id.frame_sparepart, new SparepartTampilFragment()).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sparepart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toolbar myToolBar = findViewById(R.id.sparepart_toolbar);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.tambah_sparepart_tb:
                myToolBar.setTitle("Tambah Sparepart");
                fragmentManager.beginTransaction().replace(R.id.frame_sparepart, new SparepartTambahFragment()).commit();
                Toast.makeText(this, "Tambah success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tampil_sparepart_tb:
                myToolBar.setTitle("Tampil Sparepart");
                fragmentManager.beginTransaction().replace(R.id.frame_sparepart, new SparepartTampilFragment()).commit();
                Toast.makeText(this, "Tampil success", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return true;
    }
}
