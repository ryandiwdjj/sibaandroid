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
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
import Models.cabang;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterCabang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CabangDialogFragment extends DialogFragment {
    private List<cabang> mListCabang = new ArrayList<>();
    private List<cabang> cabangList;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    RecyclerAdapterCabang recyclerAdapterCabang;
    private cabang cab;
    private String cabangJSON="";
    private Intent intent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dialog_fragment_cabang, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

//        cabangJSON="";

        //get references
        recyclerView = v.findViewById(R.id.recycler_dialog_cabang);
        recyclerAdapterCabang = new RecyclerAdapterCabang(this.getActivity(), mListCabang);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterCabang);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterCabang(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                cab = cabangList.get(position);
                //mindah data sup pake json
//                Intent intent = new Intent(getContext(), TampilSparepart.class);
//                intent.putExtra("sparepart_object", new Gson().toJson(spare));
//                startActivity(intent);

                cabangJSON = new Gson().toJson(cab);
//                Log.d("onClickCabang", cabangJSON);

//                inputData.sendCabangData(cabangJSON);

                intent = new Intent();
                intent.putExtra("data_cabang", cabangJSON);

                getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intent);
                getDialog().dismiss();
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

        Call<List<cabang>> call = apiInterface.getCabang();
        call.enqueue(new Callback<List<cabang>>() {
            @Override
            public void onResponse(Call<List<cabang>> call, Response<List<cabang>> response) {
                if(response.isSuccessful()) {
                    cabangList = response.body();

                    progressDialog.dismiss();

                    recyclerAdapterCabang.notifyDataSetChanged();
                    recyclerAdapterCabang = new RecyclerAdapterCabang(getContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterCabang);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<cabang>> call, Throwable t) {
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