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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.siba.R;

import java.util.ArrayList;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
import Models.pengadaan;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterPengadaan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengadaanTampilFragment extends Fragment {
    private List<pengadaan> mListPengadaan = new ArrayList<>();
    private List<pengadaan> pengadaanList;
    private RecyclerView recyclerView;
    public RecyclerAdapterPengadaan recyclerAdapterPengadaan;
    private RecyclerView.LayoutManager layoutManager;
    ApiInterface apiInterface;
    private pengadaan pengadaan;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pengadaan_tampil, container, false);

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
                recyclerAdapterPengadaan.getFilter().filter(newText);
                Log.d("onQueryTextChange","triggered");

                return false;
            }
        });

        //get references
        recyclerView = v.findViewById(R.id.recycler_view_pengadaan);
        recyclerAdapterPengadaan = new RecyclerAdapterPengadaan(this.getActivity(), mListPengadaan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterPengadaan);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterPengadaan(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                pengadaan = pengadaanList.get(position);

                //mindah data sup pake json
//                Intent intent = new Intent(getContext(), TampilSupplier.class);
//                intent.putExtra("supplier_object", new Gson().toJson(sup));
//                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                pengadaan = pengadaanList.get(position);
                Log.d("long click", "long click pressed");
//                Intent i = new Intent(Intent.ACTION_CALL);
//                i.setData(Uri.parse(sup.getAlamat_supplier()));
//                startActivity(i);
            }
        }));
        getPengadaan();



        return v;
    }

    private void getPengadaan() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<List<pengadaan>> call = apiInterface.getPengadaan();
        call.enqueue(new Callback<List<pengadaan>>() {
            @Override
            public void onResponse(Call<List<pengadaan>> call, Response<List<pengadaan>> response) {
                if(response.isSuccessful()) {
                    pengadaanList = response.body();

                    progressDialog.dismiss();

                    recyclerAdapterPengadaan.notifyDataSetChanged();
                    recyclerAdapterPengadaan = new RecyclerAdapterPengadaan(getContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterPengadaan);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<pengadaan>> call, Throwable t) {
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
        getPengadaan();
    }
}