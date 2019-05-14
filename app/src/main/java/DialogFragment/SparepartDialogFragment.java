package DialogFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.siba.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
import Models.sparepart;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterDetailPengadaan;
import Recycler.RecyclerAdapterPengadaan;
import Recycler.RecyclerAdapterSparepartDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SparepartDialogFragment extends DialogFragment {
    private List<sparepart> mListSparepart = new ArrayList<>();
    private List<sparepart> spareList;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    RecyclerAdapterSparepartDialog recyclerAdapterSparepartDialog;
    private sparepart spare;
    private String spareJSON="";
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dialog_fragment_sparepart, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //get references
        recyclerView = v.findViewById(R.id.recycler_dialog_sparepart);
        recyclerAdapterSparepartDialog = new RecyclerAdapterSparepartDialog(this.getActivity(), mListSparepart);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterSparepartDialog);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterPengadaan(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                spare = spareList.get(position);
                //mindah data sup pake json
//                Intent intent = new Intent(getContext(), TampilSparepart.class);
//                intent.putExtra("sparepart_object", new Gson().toJson(spare));
//                startActivity(intent);
                spareJSON = new Gson().toJson(spare);
//                Log.d("onClickSparepart", spareJSON);

                intent = new Intent();
                intent.putExtra("data_sparepart", spareJSON);

                getTargetFragment().onActivityResult(getTargetRequestCode(), 2, intent);
                getDialog().dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("onLongClick", "pressed");
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

                    recyclerAdapterSparepartDialog.notifyDataSetChanged();
                    recyclerAdapterSparepartDialog = new RecyclerAdapterSparepartDialog(getContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterSparepartDialog);
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

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        getSparepart();
    }
}
