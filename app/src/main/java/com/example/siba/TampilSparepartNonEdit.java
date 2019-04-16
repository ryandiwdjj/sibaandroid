package com.example.siba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import API.ApiClient;
import API.ApiInterface;
import Models.sparepart;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilSparepartNonEdit extends AppCompatActivity {

    private EditText kode_sparepart;
    private EditText nama_sparepart;
    private EditText merk_sparepart;
    private EditText tipe_sparepart;
    private ImageView gambar_sparepart;
    private EditText jumlah_stok_sparepart;
    private EditText harga_beli_sparepart;
    private EditText harga_jual_sparepart;
    private EditText jumlah_minimal;

    private ApiInterface apiInterface;

    private sparepart spare;

    ProgressDialog progressDialog;

    private Uri selectedImage;

    private Bitmap ImageBitmap;
    private Bitmap bitmap;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ImageBitmap=bitmap;
                Picasso.get().load(selectedImage).resize(400,400).centerCrop().into(gambar_sparepart);
            }
            catch (Exception e) {
                Log.e("onActivityResult", e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_sparepart_non_edit);

        kode_sparepart = findViewById(R.id.kode_sparepart_txt);
        nama_sparepart = findViewById(R.id.nama_sparepart_txt);
        merk_sparepart = findViewById(R.id.merk_sparepart_txt);
        tipe_sparepart = findViewById(R.id.tipe_sparepart_txt);
        gambar_sparepart = findViewById(R.id.gambar_sparepart_img); //ganti gambar
        jumlah_stok_sparepart = findViewById(R.id.jumlah_stok_sparepart_txt);
        harga_beli_sparepart = findViewById(R.id.harga_beli_sparepart_txt);
        harga_jual_sparepart = findViewById(R.id.harga_jual_sparepart_txt);
        jumlah_minimal = findViewById(R.id.jumlah_minimal_txt);

        EditTextEnabled(false);

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
            Log.d("gambar_sparepart", spare.getGambar_sparepart());
            Picasso.get().load(spare.getGambar_sparepart()).resize(400,400).centerCrop().into(gambar_sparepart);
            jumlah_stok_sparepart.setText(spare.getJumlah_stok_sparepart().toString());
            harga_beli_sparepart.setText(spare.getHarga_beli_sparepart().toString());
            harga_jual_sparepart.setText(spare.getHarga_jual_sparepart().toString());
            jumlah_minimal.setText(spare.getJumlah_minimal().toString());

            //Toast.makeText(this, spare.getId_sparepart(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
        }

        gambar_sparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
    }

    void EditTextEnabled(Boolean b){
        kode_sparepart.setEnabled(b);
        nama_sparepart.setEnabled(b);
        merk_sparepart.setEnabled(b);
        tipe_sparepart.setEnabled(b);
        //gambar_sparepart.setEnabled(b); //ganti gambar
        gambar_sparepart.setEnabled(b);
        jumlah_stok_sparepart.setEnabled(b);
        harga_beli_sparepart.setEnabled(b);
        harga_jual_sparepart.setEnabled(b);
        jumlah_minimal.setEnabled(b);
    }
}
