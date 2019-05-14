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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siba.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

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

public class DetailPengadaanDialogFragment extends DialogFragment {
    ApiInterface apiInterface;

    private EditText jumlah_beli;
    private ImageButton add_btn;
    private ImageButton remove_btn;
    private TextView cancel_btn;
    private TextView delete_btn;
    private TextView save_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dialog_fragment_detail_pengadaan, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        jumlah_beli = v.findViewById(R.id.jumlah_beli);
        add_btn = v.findViewById(R.id.button_add);
        remove_btn = v.findViewById(R.id.button_remove);
        cancel_btn = v.findViewById(R.id.batal_btn);
        delete_btn = v.findViewById(R.id.hapus_btn);
        save_btn = v.findViewById(R.id.simpan_btn);

//        add_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jumlah_beli.setText(Integer.parseInt(jumlah_beli.getText().toString()) + 1);
//            }
//        });
//
//        remove_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jumlah_beli.setText(Integer.parseInt(jumlah_beli.getText().toString()) - 1);
//            }
//        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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