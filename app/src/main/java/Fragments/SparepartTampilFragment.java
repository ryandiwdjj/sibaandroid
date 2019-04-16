package Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siba.R;
import com.example.siba.TampilSparepart;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Models.sparepart;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterSparepart;
import API.ApiClient;
import API.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SparepartTampilFragment extends Fragment {
    private List<sparepart> mListSparepart = new ArrayList<>();
    private List<sparepart> spareList;
    private RecyclerView recyclerView;
    public RecyclerAdapterSparepart recyclerAdapterSparepart;
    private RecyclerView.LayoutManager layoutManager;
    ApiInterface apiInterface;
    private sparepart spare;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sparepart_tampil, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        SearchView search_txt = v.findViewById(R.id.search_box_txt);

        search_txt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //hide window
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapterSparepart.getSearchFilter().filter(newText);
                Log.d("onQueryTextChange","triggered");

                return false;
            }
        });

        //get references
        recyclerView = v.findViewById(R.id.recycler_view_sparepart);
        recyclerAdapterSparepart = new RecyclerAdapterSparepart(this.getActivity(), mListSparepart);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterSparepart);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterSparepart(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                spare = spareList.get(position);
                //mindah data sup pake json
                Intent intent = new Intent(getContext(), TampilSparepart.class);
                intent.putExtra("sparepart_object", new Gson().toJson(spare));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                spare = spareList.get(position);
                Log.d("long click", "long click pressed");

                AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                builder.setTitle("Hapus Sparepart?");
                builder.setButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("onclickya", "yey");
//                        //method delete
//                        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//                        Call<sparepart> call = apiInterface.deleteSparepart(spare.getId_sparepart());
//
//                        call.enqueue(new Callback<sparepart>() {
//                            @Override
//                            public void onResponse(Call<sparepart> call, Response<sparepart> response) {
//                                if(response.isSuccessful()) {
//                                    Toast.makeText(getActivity(), "Sparepart Terhapus", Toast.LENGTH_SHORT).show();
//                                }
//                                else {
//                                    Toast.makeText(getActivity(), "Cek Koneksi anda", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<sparepart> call, Throwable t) {
//                                Log.e("onFailure", t.getMessage());
//
//                            }
//                        });
                    }
                });
                builder.setButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
        }));
        getSparepart();



        return v;
    }

    private void getSparepart() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<List<sparepart>> call = apiInterface.getSparepart();
        call.enqueue(new Callback<List<sparepart>>() {
            @Override
            public void onResponse(Call<List<sparepart>> call, Response<List<sparepart>> response) {
                if(response.isSuccessful()) {
                    spareList = response.body();

                    progressDialog.dismiss();

                    recyclerAdapterSparepart.notifyDataSetChanged();
                    recyclerAdapterSparepart = new RecyclerAdapterSparepart(getContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterSparepart);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<sparepart>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("onFailureTampil", t.getMessage());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //recyclerAdapterSparepart = new RecyclerAdapterSparepart(this.getActivity(), mListSparepart);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSparepart();
    }
}
