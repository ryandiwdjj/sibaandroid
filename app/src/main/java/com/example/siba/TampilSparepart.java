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
import Models.sparepart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilSparepart extends AppCompatActivity {

    private EditText kode_sparepart;
    private EditText nama_sparepart;
    private EditText merk_sparepart;
    private EditText tipe_sparepart;
    private EditText gambar_sparepart;
    private EditText jumlah_stok_sparepart;
    private EditText harga_beli_sparepart;
    private EditText harga_jual_sparepart;
    private EditText jumlah_minimal;

    private Button simpan_btn;
    private Button delete_btn;
    private Switch editable;

    private ApiInterface apiInterface;

    private sparepart spare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_sparepart);

        kode_sparepart = findViewById(R.id.kode_sparepart_txt);
        nama_sparepart = findViewById(R.id.nama_sparepart_txt);
        merk_sparepart = findViewById(R.id.merk_sparepart_txt);
        tipe_sparepart = findViewById(R.id.tipe_sparepart_txt);
        //gambar_sparepart = findViewById(R.id.gambar_sparepart_txt); //ganti gambar
        jumlah_stok_sparepart = findViewById(R.id.jumlah_stok_sparepart_txt);
        harga_beli_sparepart = findViewById(R.id.harga_beli_sparepart_txt);
        harga_jual_sparepart = findViewById(R.id.harga_jual_sparepart_txt);
        jumlah_minimal = findViewById(R.id.jumlah_minimal_txt);

        simpan_btn = findViewById(R.id.simpan_spare_btn);
        delete_btn = findViewById(R.id.hapus_spare_btn);

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
        Toolbar myToolBar = findViewById(R.id.sparepart_toolbar);
        myToolBar.setTitle("Edit Sparepart"); //ganti nama sparepart nya nanti
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
            spare = new Gson().fromJson(extras.getString("sparepart_object"), sparepart.class);

            //input data (get data from spare)
            //input data (get data from spare)

            kode_sparepart.setText(spare.getKode_sparepart());
            nama_sparepart.setText(spare.getNama_sparepart());
            merk_sparepart.setText(spare.getMerk_sparepart());
            tipe_sparepart.setText(spare.getTipe_sparepart());
//            gambar_sparepart.setText(spare.getGambar_sparepart());
            jumlah_stok_sparepart.setText(spare.getJumlah_stok_sparepart().toString());
            harga_beli_sparepart.setText(spare.getHarga_beli_sparepart().toString());
            harga_jual_sparepart.setText(spare.getHarga_jual_sparepart().toString());
            jumlah_minimal.setText(spare.getJumlah_minimal().toString());

            //Toast.makeText(this, spare.getId_sparepart(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
        }



        simpan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method simpan
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                spare.setKode_sparepart(kode_sparepart.getText().toString());
                spare.setNama_sparepart(nama_sparepart.getText().toString());
                spare.setMerk_sparepart(merk_sparepart.getText().toString());
                spare.setTipe_sparepart(tipe_sparepart.getText().toString());
//                spare.setGambar_sparepart(gambar_sparepart.getText().toString());
                spare.setGambar_sparepart("android");
                spare.setJumlah_stok_sparepart(Integer.parseInt(jumlah_stok_sparepart.getText().toString()));
                spare.setHarga_beli_sparepart(Float.parseFloat(harga_beli_sparepart.getText().toString()));
                spare.setHarga_jual_sparepart(Float.parseFloat(harga_jual_sparepart.getText().toString()));
                spare.setJumlah_minimal(Integer.parseInt(jumlah_minimal.getText().toString()));


                Call<sparepart> call = apiInterface.updateSparepart(spare.getId_sparepart(), spare);

                call.enqueue(new Callback<sparepart>() {
                    @Override
                    public void onResponse(Call<sparepart> call, Response<sparepart> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(TampilSparepart.this, "Sparepart Diperbaharui", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else {
                            Log.e("onresponseisfail", response.errorBody().toString());
                            Toast.makeText(TampilSparepart.this, "Cek Koneksi anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<sparepart> call, Throwable t) {
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
                Call<sparepart> call = apiInterface.deleteSparepart(spare.getId_sparepart());

                call.enqueue(new Callback<sparepart>() {
                    @Override
                    public void onResponse(Call<sparepart> call, Response<sparepart> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(TampilSparepart.this, "Sparepart Terhapus", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else {
                            Toast.makeText(TampilSparepart.this, "Cek Koneksi anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<sparepart> call, Throwable t) {
                        Log.e("onFailure", t.getMessage());
                        onBackPressed();
                    }
                });
            }
        });
    }

    void EditTextEnabled(Boolean b){
        kode_sparepart.setEnabled(b);
        nama_sparepart.setEnabled(b);
        merk_sparepart.setEnabled(b);
        tipe_sparepart.setEnabled(b);
        //gambar_sparepart.setEnabled(b); //ganti gambar
        jumlah_stok_sparepart.setEnabled(b);
        harga_beli_sparepart.setEnabled(b);
        harga_jual_sparepart.setEnabled(b);
        jumlah_minimal.setEnabled(b);
    }
}
