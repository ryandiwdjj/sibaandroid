package DialogFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.siba.R;
import com.example.siba.TampilPengadaan;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
import Models.sparepart;
import Models.supplier;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterSparepartDialog;
import Recycler.RecyclerAdapterSupplier;
import Recycler.RecyclerAdapterSupplierDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierDialogFragment extends DialogFragment {
    private List<supplier> mListSupplier = new ArrayList<>();
    private List<supplier> supplierList;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    RecyclerAdapterSupplierDialog recyclerAdapterSupplierDialog;
    private supplier supp;
    private String suppJSON="";
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dialog_fragment_supplier, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //get references
        recyclerView = v.findViewById(R.id.recycler_dialog_supplier);
        recyclerAdapterSupplierDialog = new RecyclerAdapterSupplierDialog(this.getActivity(), mListSupplier);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterSupplierDialog);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterSupplierDialog(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                supp = supplierList.get(position);
                //mindah data sup pake json
//                Intent intent = new Intent(getContext(), TampilSparepart.class);
//                intent.putExtra("sparepart_object", new Gson().toJson(spare));
//                startActivity(intent);

                suppJSON = new Gson().toJson(supp);
//                Log.d("onClickSupplier", suppJSON);

                intent = new Intent();
                intent.putExtra("data_supplier", suppJSON);

                try {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), 1, intent);
                    dismiss();
                }catch (Exception e) {
                    ((TampilPengadaan)getActivity()).setSupplier(supp);
                    dismiss();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("onLongClick", "pressed");
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
                    supplierList = response.body();

                    progressDialog.dismiss();

                    recyclerAdapterSupplierDialog.notifyDataSetChanged();
                    recyclerAdapterSupplierDialog = new RecyclerAdapterSupplierDialog(getContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterSupplierDialog);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<supplier>> call, Throwable t) {
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
        getSupplier();
    }
}