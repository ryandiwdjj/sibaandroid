package DialogFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
import Models.penjualan;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterHistoryDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class HistoryDialogFragment extends DialogFragment {
    private List<penjualan> mListPenjualan = new ArrayList<>();
    private List<penjualan> penjualanList;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    RecyclerAdapterHistoryDialog recyclerAdapterHistoryDialog;
    private penjualan penjualan;
    private String penjualanJSON="";
    private String data;
    private Intent intent;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dialog_fragment_history, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);
        data = sp.getString("login_cred", null);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //get references
        recyclerView = v.findViewById(R.id.recycler_dialog_history);
        recyclerAdapterHistoryDialog = new RecyclerAdapterHistoryDialog(this.getActivity(), mListPenjualan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterHistoryDialog);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterHistoryDialog(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                penjualan = penjualanList.get(position);
                //mindah data sup pake json
//                Intent intent = new Intent(getContext(), TampilSparepart.class);
//                intent.putExtra("sparepart_object", new Gson().toJson(spare));
//                startActivity(intent);

//                penjualanJSON = new Gson().toJson(penjualan);
////                Log.d("onClickSupplier", suppJSON);
//
//                intent = new Intent();
//                intent.putExtra("data_supplier", penjualanJSON);
////
//
//                getTargetFragment().onActivityResult(getTargetRequestCode(), 1, intent);
//                getDialog().dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("onLongClick", "pressed");
            }
        }));

        getHistory();
        return v;
    }

    private void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<List<penjualan>> call = apiInterface.getHistoryById(Integer.parseInt(data));
        call.enqueue(new Callback<List<penjualan>>() {
            @Override
            public void onResponse(Call<List<penjualan>> call, Response<List<penjualan>> response) {
                if(response.isSuccessful()) {
                    penjualanList = response.body();

                    progressDialog.dismiss();

                    recyclerAdapterHistoryDialog.notifyDataSetChanged();
                    recyclerAdapterHistoryDialog = new RecyclerAdapterHistoryDialog(getContext(), response.body()); //getresult()
                    Log.d("response body history", response.body().toString());
                    recyclerView.setAdapter(recyclerAdapterHistoryDialog);
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
        //recyclerAdapterSparepart = new RecyclerAdapterSparepart(this.getActivity(), mListSparepart);
    }

    @Override
    public void onResume() {
        super.onResume();
        getHistory();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
