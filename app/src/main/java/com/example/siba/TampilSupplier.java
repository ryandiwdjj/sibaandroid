package com.example.siba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import API.ApiClient;
import API.ApiInterface;
import Models.supplier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilSupplier extends AppCompatActivity {

    private EditText nama_sup;
    private EditText sales_sup;
    private EditText no_telp_sup;
    private EditText alamat_sup;
    private Button simpan_btn;
    private Button delete_btn;
    private Switch editable;

    private ApiInterface apiInterface;

    private supplier sup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_supplier);

        nama_sup = findViewById(R.id.nama_supplier_txt);
        sales_sup = findViewById(R.id.sales_supplier_txt);
        no_telp_sup = findViewById(R.id.no_telp_supplier_txt);
        alamat_sup = findViewById(R.id.alamat_supplier_txt);

        simpan_btn = findViewById(R.id.simpan_sup_btn);
        delete_btn = findViewById(R.id.hapus_sup_btn);

        editable = findViewById(R.id.edit_switch);

        editable.setChecked(false);
        EditTextEnabled(false);

        editable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editable.isChecked()) {
                    EditTextEnabled(true);
                }
                else {
                    EditTextEnabled(false);
                }
            }
        });

        //toolbar
        Toolbar myToolBar = findViewById(R.id.supplier_toolbar);
        myToolBar.setTitle("Edit Supplier");
        //myToolBar.setTitle(sup.getNama_supplier());
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        //set back on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //get json
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            sup = new Gson().fromJson(extras.getString("supplier_object"), supplier.class);
        }

        //input data (get data from sup)
        nama_sup.setText(sup.getNama_supplier());
        sales_sup.setText(sup.getSales_supplier());
        no_telp_sup.setText(sup.getNo_telp_supplier());
        alamat_sup.setText(sup.getAlamat_supplier());


        simpan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method simpan
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                sup.setNama_supplier(nama_sup.getText().toString());
                sup.setSales_supplier(sales_sup.getText().toString());
                sup.setNo_telp_supplier(no_telp_sup.getText().toString());
                sup.setAlamat_supplier(alamat_sup.getText().toString());

                Call<supplier> call = apiInterface.updateSupplier(sup.getId_supplier(), sup);

                call.enqueue(new Callback<supplier>() {
                    @Override
                    public void onResponse(Call<supplier> call, Response<supplier> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(TampilSupplier.this, "Supplier Diperbaharui", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else {
                            Toast.makeText(TampilSupplier.this, "Cek Koneksi anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<supplier> call, Throwable t) {
                        Log.e("onFailure", t.getMessage());
                        onBackPressed();
                    }
                });
            }

        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method delete
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<supplier> call = apiInterface.deleteSupplier(sup.getId_supplier());

                call.enqueue(new Callback<supplier>() {
                    @Override
                    public void onResponse(Call<supplier> call, Response<supplier> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(TampilSupplier.this, "Supplier Terhapus", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else {
                            Toast.makeText(TampilSupplier.this, "Cek Koneksi anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<supplier> call, Throwable t) {
                        Log.e("onFailure", t.getMessage());
                        onBackPressed();
                    }
                });
            }
        });
    }

    void EditTextEnabled(Boolean b){
        nama_sup.setEnabled(b);
        sales_sup.setEnabled(b);
        no_telp_sup.setEnabled(b);
        alamat_sup.setEnabled(b);
    }
}
