package Fragments;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.siba.R;

import java.util.ArrayList;
import java.util.List;

import Models.supplier;
import Recycler.RecyclerAdapter;
import API.ApiClient;
import API.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierTampilFragment extends Fragment {
    private List<supplier> mListSupplier = new ArrayList<>();
    private RecyclerView recyclerView;
    public RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ApiInterface apiInterface;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_supplier_tampil, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //get references
        recyclerView = v.findViewById(R.id.recycler_view_supplier);
        recyclerAdapter  = new RecyclerAdapter(this.getActivity(), mListSupplier);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
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
                    progressDialog.dismiss();
                    recyclerAdapter.notifyDataSetChanged();
                    recyclerAdapter = new RecyclerAdapter(getContext().getApplicationContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapter);
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
        //recyclerAdapter = new RecyclerAdapter(this.getActivity(), mListSupplier);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupplier();
    }
}
