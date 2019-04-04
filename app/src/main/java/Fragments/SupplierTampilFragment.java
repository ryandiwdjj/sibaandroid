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
import android.widget.Toast;

import com.example.siba.R;
import com.example.siba.TampilSupplier;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Models.supplier;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterSupplier;
import API.ApiClient;
import API.ApiInterface;
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

        //get references
        recyclerView = v.findViewById(R.id.recycler_view_supplier);
        recyclerAdapterSupplier = new RecyclerAdapterSupplier(this.getActivity(), mListSupplier);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterSupplier);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterSupplier(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                sup = supList.get(position);

                //mindah data sup pake json
                Intent intent = new Intent(getContext(), TampilSupplier.class);
                intent.putExtra("supplier_object", new Gson().toJson(sup));
                startActivity(intent);

                Toast.makeText(getContext(), "Single Click on position : "+sup.getId_supplier(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                sup = supList.get(position);

                //PENGEN DELETE

                Toast.makeText(getContext(), "Long Click on position : "+sup.getNo_telp_supplier(),
                        Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "yey", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "naaaaayyyy", Toast.LENGTH_SHORT).show();
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
        //recyclerAdapterSupplier = new RecyclerAdapterSupplier(this.getActivity(), mListSupplier);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupplier();
    }
}
