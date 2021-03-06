package com.example.siba;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siba.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
import DialogFragment.CabangDialogFragment;
import DialogFragment.DetailPengadaanDialogFragment;
import DialogFragment.SparepartDialogFragment;
import DialogFragment.SupplierDialogFragment;
import Fragments.PengadaanTambahFragment;
import Models.cabang;
import Models.detail_pengadaan;
import Models.sparepart;
import Models.pengadaan;
import Models.supplier;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterCabang;
import Recycler.RecyclerAdapterDetailPengadaan;
import Recycler.RecyclerAdapterSparepart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilPengadaan extends AppCompatActivity {
    private List<Models.detail_pengadaan> detail_pengadaanList = new ArrayList<>();
    private detail_pengadaan detail_pengadaan;
    private EditText nama_supplier;
    private EditText nama_cabang;
    private ImageButton tambah_cabang_btn;
    private ImageButton tambah_supplier_btn;
    private ImageButton tambah_spare_btn;
    private Button tambah_detail_btn;
    private CabangDialogFragment cabangDialogFragment;
    private String suppJSON;
    private String cabangJSON;
    private String spareJSON;
    private cabang cabangObj;
    private supplier suppObj;
    private sparepart spareObj;
    private Float sub_total_harga = (float) 0;
    //    private List<sparepart> spareList = new ArrayList<>();
    private RecyclerAdapterDetailPengadaan recyclerAdapterDetailPengadaan;
    private RecyclerView recyclerView;

    final private Integer RC_CABANG = 0;
    final private Integer RC_SUPPLIER = 1;
    final private Integer RC_SPAREPART = 2;
    final private Integer RC_DETAIL = 3;

    ArrayAdapter<String> adapter;

    ApiInterface apiInterface;

    FragmentManager manager = getSupportFragmentManager();
    private pengadaan pengadaan;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_CABANG) {
            cabangJSON = data.getStringExtra("data_cabang");
            Log.d("onActivityResultsCabang", cabangJSON);

            cabangObj = new Gson().fromJson(cabangJSON, cabang.class);

            nama_cabang.setText(cabangObj.getNama_cabang());
        } else if (resultCode == RC_SUPPLIER) {
            suppJSON = data.getStringExtra("data_supplier");
            Log.d("onActivityResultsSupp", suppJSON);

            suppObj = new Gson().fromJson(suppJSON, supplier.class);

            nama_supplier.setText(suppObj.getNama_supplier());
        } else if (resultCode == RC_SPAREPART) {
            spareJSON = data.getStringExtra("data_sparepart");
            Log.d("onActivityResultsSpare", spareJSON);
            spareObj = new Gson().fromJson(spareJSON, sparepart.class);

            detail_pengadaan = new detail_pengadaan(spareObj.getId_sparepart(), 1, spareObj.getNama_sparepart(),
                    spareObj.getGambar_sparepart(), spareObj.getHarga_beli_sparepart());

            //if data was added then add exception
            if (contains(detail_pengadaanList, spareObj)) {
                Toast.makeText(getApplicationContext(), "Data Sudah Ada!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("added to list", detail_pengadaan.getNama_sparepart());
                detail_pengadaanList.add(detail_pengadaan);

                //update recyclerview
                recyclerAdapterDetailPengadaan.notifyDataSetChanged();
                recyclerAdapterDetailPengadaan = new RecyclerAdapterDetailPengadaan(getApplicationContext(), detail_pengadaanList); //getresult()
                recyclerView.setAdapter(recyclerAdapterDetailPengadaan);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean contains(List<detail_pengadaan> detail_pengadaanList, sparepart spare) {
        for (detail_pengadaan item : detail_pengadaanList) {
            Log.d("check data", "welcome!");
            if (item.getId_sparepart().equals(spare.getId_sparepart())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_pengadaan);

        Toolbar myToolBar = findViewById(R.id.penjualan_toolbar);
        myToolBar.setTitle("Update Penjualan");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c);

        Log.d("date now", formattedDate);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        nama_supplier = findViewById(R.id.nama_supplier_txt);
        nama_cabang = findViewById(R.id.nama_cabang_txt);
        tambah_cabang_btn = findViewById(R.id.tambah_cabang_pengadaan_btn);
        tambah_supplier_btn = findViewById(R.id.tambah_supplier_pengadaan_btn);
        tambah_spare_btn = findViewById(R.id.tambah_spare_pengadaan_btn);
        tambah_detail_btn = findViewById(R.id.simpan_detail_btn);

        nama_supplier.setEnabled(false);
        nama_cabang.setEnabled(false);

        //get data
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pengadaan = new Gson().fromJson(extras.getString("pengadaan_object"), pengadaan.class);

            try{
                nama_supplier.setText(pengadaan.getNama_supplier());
                nama_cabang.setText(pengadaan.getNama_cabang());
            } catch(Exception e) {
                Toast.makeText(this, "Cek Koneksi anda!", Toast.LENGTH_SHORT).show();
            }
        }

        tambah_cabang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CabangDialogFragment dialog = new CabangDialogFragment();
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });

        tambah_supplier_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierDialogFragment dialog = new SupplierDialogFragment();
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });

        tambah_spare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "onClick: opening dialog.");

                SparepartDialogFragment dialog = new SparepartDialogFragment();
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });

        tambah_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tambah_detail_btn", "clicked");

                try {
                    Log.d("id_supplier", suppObj.getId_supplier().toString());
                    Log.d("id_cabang", cabangObj.getId_cabang().toString());
                    Call<pengadaan> call = apiInterface.addPengadaan(suppObj.getId_supplier()
                            , cabangObj.getId_cabang(), formattedDate);


                    call.enqueue(new Callback<pengadaan>() {
                        @Override
                        public void onResponse(Call<pengadaan> call, Response<pengadaan> response) {
                            if (response.isSuccessful()) {
                                Log.d("responseisSuccess", String.valueOf(response.body().getId_pengadaan()));

                                //add detail pengadaan
                                try {
                                    for (detail_pengadaan items : detail_pengadaanList) {
                                        Log.d("detail_pengadaan item", items.getNama_sparepart());
                                        Call<detail_pengadaan> pengadaanCall = apiInterface.addDetailPengadaan(response.body().getId_pengadaan(), items.getId_sparepart(), items.getJumlah_pengadaan());
                                        pengadaanCall.enqueue(new Callback<Models.detail_pengadaan>() {
                                            @Override
                                            public void onResponse(Call<Models.detail_pengadaan> call, Response<Models.detail_pengadaan> response) {
                                                if (response.isSuccessful()) {
                                                } else {

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Models.detail_pengadaan> call, Throwable t) {
                                                t.getMessage();
                                            }
                                        });
                                    }

                                    Toast.makeText(getApplicationContext(), "Add Pengadaan Done!", Toast.LENGTH_SHORT).show();

                                    onBackPressed();

                                } catch (Exception e) {
                                    e.getMessage();
                                }

                            } else {
                                Log.d("responseisFail", response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<pengadaan> call, Throwable t) {
                            Log.d("onFailure", t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //get references
        recyclerView = findViewById(R.id.recycler_detail_pengadaan);
        recyclerAdapterDetailPengadaan = new RecyclerAdapterDetailPengadaan(this, detail_pengadaanList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterDetailPengadaan);

//        detail_pengadaanList = new ArrayList<>(spareList);
//        detail_pengadaanList = spareList;

        recyclerView.addOnItemTouchListener(new RecyclerAdapterDetailPengadaan(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("countSpareList", String.valueOf(detail_pengadaanList.size()));
                detail_pengadaan = detail_pengadaanList.get(position);

                DetailPengadaanDialogFragment dialog = new DetailPengadaanDialogFragment();

                Bundle bundle = new Bundle();

                bundle.putString("jumlah_beli", "1");
                dialog.show(getSupportFragmentManager(), "dialog");
            }

            @Override
            public void onLongClick(View view, int position) {
                detail_pengadaan = detail_pengadaanList.get(position);
                Log.d("long click", "long click pressed");
//                Intent i = new Intent(Intent.ACTION_CALL);
//                i.setData(Uri.parse(sup.getAlamat_supplier()));
//                startActivity(i);
            }
        }));
    }
}
