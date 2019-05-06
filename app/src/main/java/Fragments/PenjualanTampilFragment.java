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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
import Models.penjualan;
import Models.supplier;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterPenjualan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanTampilFragment extends Fragment {
    private List<penjualan> mListPenjualan = new ArrayList<>();
    private List<penjualan> penjualanList;
    private RecyclerView recyclerView;
    public RecyclerAdapterPenjualan recyclerAdapterPenjualan;
    private RecyclerView.LayoutManager layoutManager;
    ApiInterface apiInterface;
    private penjualan penj;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_penjualan_tampil, container, false);

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
                recyclerAdapterPenjualan.getFilter().filter(newText);
                Log.d("onQueryTextChange","triggered");

                return false;
            }
        });

        //get references
        recyclerView = v.findViewById(R.id.recycler_view_penjualan);
        recyclerAdapterPenjualan = new RecyclerAdapterPenjualan(this.getActivity(), mListPenjualan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterPenjualan);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterPenjualan(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                penj = penjualanList.get(position);

                //mindah data sup pake json
//                Intent intent = new Intent(getContext(), TampilSupplier.class);
//                intent.putExtra("supplier_object", new Gson().toJson(sup));
//                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                penj = penjualanList.get(position);
                Log.d("long click", "long click pressed");
//                Intent i = new Intent(Intent.ACTION_CALL);
//                i.setData(Uri.parse(sup.getAlamat_supplier()));
//                startActivity(i);
            }
        }));
        getPenjualan();



        return v;
    }

    private void getPenjualan() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<List<penjualan>> call = apiInterface.getPenjualan();
        call.enqueue(new Callback<List<penjualan>>() {
            @Override
            public void onResponse(Call<List<penjualan>> call, Response<List<penjualan>> response) {
                if(response.isSuccessful()) {
                    penjualanList = response.body();

                    progressDialog.dismiss();

                    recyclerAdapterPenjualan.notifyDataSetChanged();
                    recyclerAdapterPenjualan = new RecyclerAdapterPenjualan(getContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterPenjualan);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<penjualan>> call, Throwable t) {
                progressDialog.dismiss();

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
        getPenjualan();
    }
}
