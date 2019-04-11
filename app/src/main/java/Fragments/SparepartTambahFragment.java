package Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.siba.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import Models.sparepart;
import API.ApiClient;
import API.ApiInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class SparepartTambahFragment extends Fragment {

    private EditText kode_sparepart;
    private EditText nama_sparepart;
    private EditText merk_sparepart;
    private EditText tipe_sparepart;
    //    private EditText gambar_sparepart;
    private ImageView gambar_sparepart;
    private EditText jumlah_stok_sparepart;
    private EditText harga_beli_sparepart;
    private EditText harga_jual_sparepart;
    private EditText jumlah_minimal;
    private Button tambah_sparepart_btn;

    ProgressDialog progressDialog;

    private Uri selectedImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sparepart_tambah, container, false);
        kode_sparepart = v.findViewById(R.id.kode_sparepart_txt);
        nama_sparepart = v.findViewById(R.id.nama_sparepart_txt);
        merk_sparepart = v.findViewById(R.id.merk_sparepart_txt);
        tipe_sparepart = v.findViewById(R.id.tipe_sparepart_txt);
//        gambar_sparepart = v.findViewById(R.id.gambar_sparepart_txt); //ganti gambar
        gambar_sparepart = v.findViewById(R.id.gambar_sparepart_img);

        jumlah_stok_sparepart = v.findViewById(R.id.jumlah_stok_sparepart_txt);
        harga_beli_sparepart = v.findViewById(R.id.harga_beli_sparepart_txt);
        harga_jual_sparepart = v.findViewById(R.id.harga_jual_sparepart_txt);
        jumlah_minimal = v.findViewById(R.id.jumlah_minimal_txt);
        tambah_sparepart_btn = v.findViewById(R.id.tambah_sparepart_btn);

        gambar_sparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        tambah_sparepart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    if (kode_sparepart.getText().toString().isEmpty() ||
                            nama_sparepart.getText().toString().isEmpty() ||
                            merk_sparepart.getText().toString().isEmpty() ||
                            tipe_sparepart.getText().toString().isEmpty() ||
//                gambar_sparepart.getText().toString().isEmpty() ||  //ganti gambar
                            jumlah_stok_sparepart.getText().toString().isEmpty() ||
                            harga_beli_sparepart.getText().toString().isEmpty() ||
                            harga_jual_sparepart.getText().toString().isEmpty() ||
                            jumlah_minimal.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Kotak harus terisi!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Saving...");
                        progressDialog.show();

                        uploadFile(//selectedImage,
                                kode_sparepart.getText().toString(), nama_sparepart.getText().toString()
                        ,merk_sparepart.getText().toString(), tipe_sparepart.getText().toString(), Integer.parseInt(jumlah_stok_sparepart.getText().toString())
                        ,Float.parseFloat(harga_beli_sparepart.getText().toString()), Float.parseFloat(harga_jual_sparepart.getText().toString())
                        ,Integer.parseInt(jumlah_minimal.getText().toString()));

//                        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
////            Call<sparepart> call = apiInterface.addSparepart(kode_sparepart.getText().toString(),
////                    nama_sparepart.getText().toString(),merk_sparepart.getText().toString(),
////                    tipe_sparepart.getText().toString(),gambar_sparepart.getText().toString(), //with image
////                    Integer.parseInt(jumlah_stok_sparepart.getText().toString()),Float.parseFloat(harga_beli_sparepart.getText().toString()),
////                    Float.parseFloat(harga_jual_sparepart.getText().toString()),Integer.parseInt(jumlah_minimal.getText().toString()));
//
//                        Call<sparepart> call = apiInterface.addSparepart(kode_sparepart.getText().toString(),
//                                nama_sparepart.getText().toString(), merk_sparepart.getText().toString(),
//                                tipe_sparepart.getText().toString(),
//                                Integer.parseInt(jumlah_stok_sparepart.getText().toString()), Float.parseFloat(harga_beli_sparepart.getText().toString()),
//                                Float.parseFloat(harga_jual_sparepart.getText().toString()), Integer.parseInt(jumlah_minimal.getText().toString()));
//
//
//                        call.enqueue(new Callback<sparepart>() {
//                            @Override
//                            public void onResponse(Call<sparepart> call, Response<sparepart> response) {
//                                progressDialog.dismiss();
//                                if (response.isSuccessful()) {
//                                    Toast.makeText(getContext().getApplicationContext(), "Sparepart ditambah", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(getContext().getApplicationContext(), "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<sparepart> call, Throwable t) {
//                                progressDialog.dismiss();
//                                Log.e("On Failure", t.getMessage());
//                            }
//                        });
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            gambar_sparepart.setImageURI(selectedImage);
        }
    }

    private void uploadFile(//Uri fileUri,
                            String kode_sparepart, String nama_sparepart,
                            String merk_sparepart, String tipe_sparepart, Integer jumlah_stok_sparepart,
                            Float harga_beli, Float harga_jual, Integer jumlah_minimal) {

        //creating a file
//        File file = new File(getRealPathFromURI(fileUri));

//        File file = new File(gambar_sparepart);

        //creating request body for file
//        MultipartBody.Part requestFile = MultipartBody.Part.createFormData("picture", file.getName(),
//                RequestBody.create(MediaType.parse("image/*"), file));
//                RequestBody.create(MediaType.parse(getContext().getApplicationContext().getContentResolver().getType(fileUri)), file);
        RequestBody kode_spare = RequestBody.create(MediaType.parse("text"), kode_sparepart);
        RequestBody nama_spare = RequestBody.create(MediaType.parse("text"), nama_sparepart);
        RequestBody merk_spare = RequestBody.create(MediaType.parse("text"), merk_sparepart);
        RequestBody tipe_spare = RequestBody.create(MediaType.parse("text"), tipe_sparepart);
        RequestBody gambar_spare = RequestBody.create(MediaType.parse("text"), "null");
        RequestBody jumlah_stok = RequestBody.create(MediaType.parse("number"), jumlah_stok_sparepart.toString());
        RequestBody harga_beli_spare = RequestBody.create(MediaType.parse("text"), harga_beli.toString());
        RequestBody harga_jual_spare = RequestBody.create(MediaType.parse("text"), harga_jual.toString());
        RequestBody jumlah_min = RequestBody.create(MediaType.parse("text"), jumlah_minimal.toString());

        //creating our api
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //creating a call and calling the upload image method
//        Call<sparepart> call = apiInterface.addSparepartWImage(requestFile, kode_spare, nama_spare, merk_spare
//        ,tipe_spare, jumlah_stok, harga_beli_spare, harga_jual_spare, jumlah_min);

        Call<sparepart> call = apiInterface.addSparepart(kode_spare, nama_spare, merk_spare
                ,tipe_spare, gambar_spare, jumlah_stok, harga_beli_spare, harga_jual_spare, jumlah_min);

        //finally performing the call
        call.enqueue(new Callback<sparepart>() {
            @Override
            public void onResponse(Call<sparepart> call, Response<sparepart> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext().getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    private String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        CursorLoader loader = new CursorLoader(getContext().getApplicationContext(), contentUri, proj, null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//        cursor.close();
//        return result;
//    }
}
