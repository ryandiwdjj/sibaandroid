package com.example.siba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TampilSparepart extends AppCompatActivity {

    private EditText kode_sparepart;
    private EditText nama_sparepart;
    private EditText merk_sparepart;
    private EditText tipe_sparepart;
    private ImageView gambar_sparepart;
    private EditText jumlah_stok_sparepart;
    private EditText harga_beli_sparepart;
    private EditText harga_jual_sparepart;
    private EditText jumlah_minimal;

    private Button simpan_btn;
    private Button delete_btn;
    private Switch editable;

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
        setContentView(R.layout.activity_tampil_sparepart);

        kode_sparepart = findViewById(R.id.kode_sparepart_txt);
        nama_sparepart = findViewById(R.id.nama_sparepart_txt);
        merk_sparepart = findViewById(R.id.merk_sparepart_txt);
        tipe_sparepart = findViewById(R.id.tipe_sparepart_txt);
        gambar_sparepart = findViewById(R.id.gambar_sparepart_img); //ganti gambar
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



        simpan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    if (kode_sparepart.getText().toString().isEmpty() ||
                            nama_sparepart.getText().toString().isEmpty() ||
                            merk_sparepart.getText().toString().isEmpty() ||
                            tipe_sparepart.getText().toString().isEmpty() ||
                            jumlah_stok_sparepart.getText().toString().isEmpty() ||
                            harga_beli_sparepart.getText().toString().isEmpty() ||
                            harga_jual_sparepart.getText().toString().isEmpty() ||
                            jumlah_minimal.getText().toString().isEmpty()) {
                        Toast.makeText(TampilSparepart.this, "Kotak harus terisi!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog = new ProgressDialog(TampilSparepart.this);
                        progressDialog.setMessage("Saving...");
                        progressDialog.show();

                        updateFile(spare.getId_sparepart(), selectedImage, kode_sparepart.getText().toString(), nama_sparepart.getText().toString()
                                ,merk_sparepart.getText().toString(), tipe_sparepart.getText().toString(), Integer.parseInt(jumlah_stok_sparepart.getText().toString())
                                ,Float.parseFloat(harga_beli_sparepart.getText().toString()), Float.parseFloat(harga_jual_sparepart.getText().toString())
                                ,Integer.parseInt(jumlah_minimal.getText().toString()));

                    }
                }

                //method simpan
//                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//                RequestBody kode_spare = RequestBody.create(MediaType.parse("multipart/form-data"), kode_sparepart);
//                RequestBody nama_spare = RequestBody.create(MediaType.parse("multipart/form-data"), nama_sparepart);
//                RequestBody merk_spare = RequestBody.create(MediaType.parse("multipart/form-data"), merk_sparepart);
//                RequestBody tipe_spare = RequestBody.create(MediaType.parse("multipart/form-data"), tipe_sparepart);
////        RequestBody gambar_spare = RequestBody.create(MediaType.parse("text"), "null");
//                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
//                RequestBody jumlah_stok = RequestBody.create(MediaType.parse("multipart/form-data"), jumlah_stok_sparepart.toString());
//                RequestBody harga_beli_spare = RequestBody.create(MediaType.parse("multipart/form-data"), harga_beli.toString());
//                RequestBody harga_jual_spare = RequestBody.create(MediaType.parse("multipart/form-data"), harga_jual.toString());
//                RequestBody jumlah_min = RequestBody.create(MediaType.parse("multipart/form-data"), jumlah_minimal.toString());
//
//
//                Call<sparepart> call = apiInterface.updateSparepart(spare.getId_sparepart(), spare);
//
//                call.enqueue(new Callback<sparepart>() {
//                    @Override
//                    public void onResponse(Call<sparepart> call, Response<sparepart> response) {
//                        if(response.isSuccessful()) {
//                            Toast.makeText(TampilSparepart.this, "Sparepart Diperbaharui", Toast.LENGTH_SHORT).show();
//                            onBackPressed();
//                        }
//                        else {
//                            Log.e("onresponseisfail", response.errorBody().toString());
//                            Toast.makeText(TampilSparepart.this, "Cek Koneksi anda", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<sparepart> call, Throwable t) {
//                        Log.e("onFailure", t.getMessage());
//                        onBackPressed();
//                    }
//                });
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
        gambar_sparepart.setEnabled(b);
        jumlah_stok_sparepart.setEnabled(b);
        harga_beli_sparepart.setEnabled(b);
        harga_jual_sparepart.setEnabled(b);
        jumlah_minimal.setEnabled(b);
    }

    private void updateFile(Integer id_sparepart, Uri fileUri, String kode_sparepart, String nama_sparepart,
                            String merk_sparepart, String tipe_sparepart, Integer jumlah_stok_sparepart,
                            Float harga_beli, Float harga_jual, Integer jumlah_minimal) {
        //creating a file
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] file =baos.toByteArray();
//        File file = new File(getRealPathFromURI(fileUri));

        //RequestBody id_spare = RequestBody.create(MediaType.parse("multipart/form-data"), id_sparepart.toString());
        RequestBody kode_spare = RequestBody.create(MediaType.parse("multipart/form-data"), kode_sparepart);
        RequestBody nama_spare = RequestBody.create(MediaType.parse("multipart/form-data"), nama_sparepart);
        RequestBody merk_spare = RequestBody.create(MediaType.parse("multipart/form-data"), merk_sparepart);
        RequestBody tipe_spare = RequestBody.create(MediaType.parse("multipart/form-data"), tipe_sparepart);
//        RequestBody gambar_spare = RequestBody.create(MediaType.parse("text"), "null");
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody jumlah_stok = RequestBody.create(MediaType.parse("multipart/form-data"), jumlah_stok_sparepart.toString());
        RequestBody harga_beli_spare = RequestBody.create(MediaType.parse("multipart/form-data"), harga_beli.toString());
        RequestBody harga_jual_spare = RequestBody.create(MediaType.parse("multipart/form-data"), harga_jual.toString());
        RequestBody jumlah_min = RequestBody.create(MediaType.parse("multipart/form-data"), jumlah_minimal.toString());

        //creating our api
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        MultipartBody.Part gambar = MultipartBody.Part.createFormData("gambar_sparepart", "image.jpg", requestFile);

        //creating a call and calling the upload image method
        Call<sparepart> call = apiInterface.updateSparepart(id_sparepart, kode_spare, nama_spare, merk_spare
                ,tipe_spare, gambar, jumlah_stok, harga_beli_spare, harga_jual_spare, jumlah_min);


        //finally performing the call
        call.enqueue(new Callback<sparepart>() {
            @Override
            public void onResponse(Call<sparepart> call, Response<sparepart> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    try {
                        Log.e("onresponse error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<sparepart> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
