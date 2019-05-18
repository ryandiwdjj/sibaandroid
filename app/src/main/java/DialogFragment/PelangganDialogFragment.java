package DialogFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siba.R;

import API.ApiClient;
import API.ApiInterface;
import Models.pelanggan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PelangganDialogFragment extends DialogFragment {
    ApiInterface apiInterface;
    private String data;

    private EditText nama_pelanggan;
    private EditText no_telp_pelanggan;
    private EditText alamat_pelanggan;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dialog_fragment_pelanggan, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        nama_pelanggan = v.findViewById(R.id.nama_pelanggan_txt);
        no_telp_pelanggan = v.findViewById(R.id.no_telp_pelanggan_txt);
        alamat_pelanggan = v.findViewById(R.id.alamat_pelanggan_txt);

        nama_pelanggan.setEnabled(false);
        no_telp_pelanggan.setEnabled(false);
        alamat_pelanggan.setEnabled(false);

        try {
            data = getArguments().getString("data");
            Log.d("string", data);


            progressDialog.setMessage("Loading...");
            progressDialog.show();

            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<pelanggan> call = apiInterface.loginPelanggan(data);

            call.enqueue(new Callback<pelanggan>() {
                @Override
                public void onResponse(Call<pelanggan> call, Response<pelanggan> response) {
                    if(response.isSuccessful()) {
                        nama_pelanggan.setText(response.body().getNama_pelanggan());
                        no_telp_pelanggan.setText(response.body().getNo_telp_pelanggan());
                        alamat_pelanggan.setText(response.body().getAlamat_pelanggan());
                    }
                    else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<pelanggan> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e) {
            progressDialog.dismiss();
            Log.e("exception", e.getMessage());
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return v;
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
    }
}