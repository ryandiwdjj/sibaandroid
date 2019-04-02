package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.siba.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.supplier;
import Recycler.RecyclerAdapter;
import Remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupplierTampilFragment extends Fragment {
    private String url = "http://192.168.10.42/api/";
    private List<supplier> mListSupplier = new ArrayList<>();
    private RecyclerView recyclerView;
    public RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_supplier_tampil, container, false);
        //get references
        recyclerView = v.findViewById(R.id.recycler_view_supplier);
        recyclerAdapter  = new RecyclerAdapter(this.getActivity(), mListSupplier);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        setRecyclerView();
        return v;
    }

    private void setRecyclerView() {
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserService userService = retrofit.create(UserService.class);
        Call<List<supplier>> supplierCall = userService.getSuppliers();

        supplierCall.enqueue(new Callback<List<supplier>>() {
            @Override
            public void onResponse(Call<List<supplier>> call, Response<List<supplier>> response) {
                recyclerAdapter.notifyDataSetChanged();
                recyclerAdapter = new RecyclerAdapter(getContext().getApplicationContext(), response.body()); //getresult()
                recyclerView.setAdapter(recyclerAdapter);
                Toast.makeText(getContext(), "yey", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<supplier>> call, Throwable t) {
                Toast.makeText(getContext(), "naayy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //recyclerAdapter = new RecyclerAdapter(this.getActivity(), mListSupplier);
    }
}
