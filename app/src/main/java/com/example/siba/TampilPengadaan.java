package com.example.siba;

import android.app.ProgressDialog;
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
    public EditText nama_supplier;
    public EditText nama_cabang;
    public TextView total_harga;
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

    public void setCabang(cabang cab) {
        this.cabangObj = cab;

        Log.d("cabang obj created", cabangObj.toString());
        nama_cabang.setText(cabangObj.getNama_cabang());
    }

    public void setSupplier(supplier supplier) {
        this.suppObj = supplier;

        Log.d("supplier obj created", suppObj.toString());
        nama_supplier.setText(suppObj.getNama_supplier());
    }

    public void updateSparepart(Integer temp_jumlah, Integer temp_spare_pos) {
        Log.d("onActivityResult", temp_jumlah.toString() + " and " + temp_spare_pos.toString());

        detail_pengadaanList.get(temp_spare_pos).setJumlah_pengadaan(temp_jumlah);

        //update recyclerview
        recyclerAdapterDetailPengadaan.notifyDataSetChanged();
        recyclerAdapterDetailPengadaan = new RecyclerAdapterDetailPengadaan(this, detail_pengadaanList); //getresult()
        recyclerView.setAdapter(recyclerAdapterDetailPengadaan);
    }

    public void deleteSparepart(Integer temp_jumlah, Integer temp_spare_pos) {

        Log.d("onActivityResult Delete", temp_jumlah.toString() + " and " + temp_spare_pos.toString());

        detail_pengadaanList.remove(detail_pengadaanList.get(temp_spare_pos));

        if(!detail_pengadaanList.isEmpty()) {
            for (detail_pengadaan items : detail_pengadaanList) {
                Log.d("detail_pengadaanList", "berapa");
            }
        }

        //olah data sub total harga
//        sub_total_harga = sub_total_harga - (spareObj.getHarga_beli_sparepart() * 1);
//        total_harga.setText(sub_total_harga.toString());

        //update recyclerview
        recyclerAdapterDetailPengadaan.notifyDataSetChanged();
        recyclerAdapterDetailPengadaan = new RecyclerAdapterDetailPengadaan(this, detail_pengadaanList); //getresult()
        recyclerView.setAdapter(recyclerAdapterDetailPengadaan);
    }

    public void setSparepart(sparepart spare) {
        this.spareObj = spare;

        detail_pengadaan = new detail_pengadaan(spareObj.getId_sparepart(), 1, spareObj.getNama_sparepart(),
                spareObj.getGambar_sparepart(), spareObj.getHarga_beli_sparepart());

        //if data was added then add exception
        if (contains(detail_pengadaanList, spareObj)) {
            Toast.makeText(getApplicationContext(), "Data Sudah Ada!", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("added to list", detail_pengadaan.getNama_sparepart());
            detail_pengadaanList.add(detail_pengadaan);

            //olah data sub total harga
//            sub_total_harga = sub_total_harga + (spareObj.getHarga_beli_sparepart() * 1);
//            total_harga.setText(sub_total_harga.toString());

            //update recyclerview
            recyclerAdapterDetailPengadaan.notifyDataSetChanged();
            recyclerAdapterDetailPengadaan = new RecyclerAdapterDetailPengadaan(getApplicationContext(), detail_pengadaanList); //getresult()
            recyclerView.setAdapter(recyclerAdapterDetailPengadaan);
        }
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
        total_harga = findViewById(R.id.total_harga_pengadaaan);

        tambah_cabang_btn = findViewById(R.id.tambah_cabang_pengadaan_btn);
        tambah_supplier_btn = findViewById(R.id.tambah_supplier_pengadaan_btn);
        tambah_spare_btn = findViewById(R.id.tambah_spare_pengadaan_btn);
        tambah_detail_btn = findViewById(R.id.simpan_detail_btn);

        nama_supplier.setEnabled(false);
        nama_cabang.setEnabled(false);

        //get data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pengadaan = new Gson().fromJson(extras.getString("pengadaan_object"), pengadaan.class);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            nama_supplier.setText(pengadaan.getNama_supplier());
            nama_cabang.setText(pengadaan.getNama_cabang());

            Call<List<detail_pengadaan>> call = apiInterface.getDetailPengadaanById(pengadaan.getId_pengadaan());
            call.enqueue(new Callback<List<Models.detail_pengadaan>>() {
                @Override
                public void onResponse(Call<List<Models.detail_pengadaan>> call, Response<List<Models.detail_pengadaan>> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        detail_pengadaanList = response.body();

                        Log.d("isSuccess", response.toString());

                        recyclerAdapterDetailPengadaan.notifyDataSetChanged();
                        recyclerAdapterDetailPengadaan = new RecyclerAdapterDetailPengadaan(getApplicationContext(), detail_pengadaanList); //getresult()
                        recyclerView.setAdapter(recyclerAdapterDetailPengadaan);
                    } else { //response is failed
                        progressDialog.dismiss();

                        Toast.makeText(TampilPengadaan.this, "Gagal memuat!", Toast.LENGTH_SHORT).show();
                        Log.e("isFailed", response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Models.detail_pengadaan>> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(TampilPengadaan.this, "Cek Koneksi Anda!", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure", t.getMessage());
                }
            });

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
                                                    Log.d("responseisSuccess", String.valueOf(response.body()));
                                                    Toast.makeText(TampilPengadaan.this, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Log.d("ifFailed", response.message());
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

                bundle.putString("jumlah_beli", detail_pengadaan.getJumlah_pengadaan().toString());
                bundle.putString("spare_pos", String.valueOf(position));

                dialog.setArguments(bundle);
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
