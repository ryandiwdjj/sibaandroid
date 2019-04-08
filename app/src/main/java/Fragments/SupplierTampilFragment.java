package Fragments;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.siba.R;
import com.example.siba.TampilSupplier;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Models.supplier;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterSparepart;
import API.ApiClient;
import API.ApiInterface;
import Recycler.RecyclerAdapterSupplier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierTampilFragment extends Fragment {
    private List<supplier> mListSupplier = new ArrayList<>();
    private List<supplier> supList;
    private RecyclerView recyclerView;
    public RecyclerAdapterSupplier recyclerAdapterSupplier;
    private RecyclerView.LayoutManager layoutManager;
    ApiInterface apiInterface;
    private supplier sup;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_supplier_tampil, container, false);

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
                recyclerAdapterSupplier.getFilter().filter(newText);
                Log.d("onQueryTextChange","triggered");

//                Call<List<supplier>> call = apiInterface.getSupplierByName(newText);
//
//                call.enqueue(new Callback<List<supplier>>() {
//                    @Override
//                    public void onResponse(Call<List<supplier>> call, Response<List<supplier>> response) {
//                        recyclerAdapterSupplier.notifyDataSetChanged();
//                        recyclerAdapterSupplier = new RecyclerAdapterSparepart(getContext(), response.body());
//                        recyclerView.setAdapter(recyclerAdapterSupplier);
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<supplier>> call, Throwable t) {
//
//                    }
//                });

                return false;
            }
        });

        //get references
        recyclerView = v.findViewById(R.id.recycler_view_supplier);
        recyclerAdapterSupplier = new RecyclerAdapterSupplier(this.getActivity(), mListSupplier);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterSupplier);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterSparepart(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                sup = supList.get(position);

                //mindah data sup pake json
                Intent intent = new Intent(getContext(), TampilSupplier.class);
                intent.putExtra("supplier_object", new Gson().toJson(sup));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                sup = supList.get(position);
                Log.d("long click", "long click pressed");
//                Intent i = new Intent(Intent.ACTION_CALL);
//                i.setData(Uri.parse(sup.getAlamat_supplier()));
//                startActivity(i);
            }
        }));
        getSupplier();



        return v;
    }

    private void getSupplier() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<List<supplier>> call = apiInterface.getSupplier();
        call.enqueue(new Callback<List<supplier>>() {
            @Override
            public void onResponse(Call<List<supplier>> call, Response<List<supplier>> response) {
                if(response.isSuccessful()) {
                    supList = response.body();

                    progressDialog.dismiss();

                    recyclerAdapterSupplier.notifyDataSetChanged();
                    recyclerAdapterSupplier = new RecyclerAdapterSupplier(getContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterSupplier);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<supplier>> call, Throwable t) {
                Log.e("onFailureTampil", t.getMessage());
        }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //recyclerAdapterSupplier = new RecyclerAdapterSparepart(this.getActivity(), mListSupplier);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupplier();
    }
}
