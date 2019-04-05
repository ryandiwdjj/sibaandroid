package com.example.siba;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import Fragments.SupplierCariFragment;
import Fragments.SupplierTambahFragment;
import Fragments.SupplierTampilFragment;
import Recycler.RecyclerAdapterSupplier;

public class OwnerSupplierActivity extends AppCompatActivity {
    //fragment declaration
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_supplier);

        Toolbar myToolBar = findViewById(R.id.supplier_toolbar);
        myToolBar.setTitle("Tampil Supplier");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        fragmentManager.beginTransaction().replace(R.id.frame_supplier, new SupplierTampilFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.supplier_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toolbar myToolBar = findViewById(R.id.supplier_toolbar);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.tambah_supplier_tb:
                myToolBar.setTitle("Tambah Supplier");
                fragmentManager.beginTransaction().replace(R.id.frame_supplier, new SupplierTambahFragment()).commit();
//                Toast.makeText(this, "Tambah success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tampil_supplier_tb:
                myToolBar.setTitle("Tampil Supplier");
                fragmentManager.beginTransaction().replace(R.id.frame_supplier, new SupplierTampilFragment()).commit();
//                Toast.makeText(this, "Tampil success", Toast.LENGTH_SHORT).show();
                break;

                default:
                    break;
        }
        return true;
    }
}
