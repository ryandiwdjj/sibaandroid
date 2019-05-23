package DialogFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siba.R;
import com.example.siba.TampilPengadaan;
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
//    private ImageButton add_btn;
//    private ImageButton remove_btn;
    private Button cancel_btn;
    private Button delete_btn;
    private Button save_btn;

    private String spare_pos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dialog_fragment_detail_pengadaan, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        jumlah_beli = v.findViewById(R.id.jumlah_beli);
//        add_btn = v.findViewById(R.id.button_add);
//        remove_btn = v.findViewById(R.id.button_remove);
        cancel_btn = v.findViewById(R.id.batal_btn);
        delete_btn = v.findViewById(R.id.hapus_btn);
        save_btn = v.findViewById(R.id.simpan_btn);

        try {
            Bundle bundle = getArguments();
            jumlah_beli.setText(bundle.getString("jumlah_beli"));
            spare_pos = bundle.getString("spare_pos");

            Log.d("getArgument", jumlah_beli.getText().toString() + " and " + spare_pos);
        }
        catch (Exception e) {
            Log.e("exception catch", e.getMessage());
        }

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
                //delete sparepart tersebut
                Log.d("delete onClick", "pressed");

                Intent intent = new Intent();

                intent.putExtra("spare_pos", spare_pos);
                intent.putExtra("jumlah_pengadaan", jumlah_beli.getText().toString());

                try {
                    Log.d("onClick", "pressed");
                    getTargetFragment().onActivityResult(getTargetRequestCode(), 4, intent);
                    dismiss();
                }catch (Exception e) {
                    Log.e("exception delete_btn", e.getMessage());
                    ((TampilPengadaan)getActivity())
                            .deleteSparepart(Integer.parseInt(jumlah_beli.getText().toString()), Integer.parseInt(spare_pos));
                    dismiss();
                }
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(jumlah_beli.getText().toString()) < 0 ) {
                    Toast.makeText(getActivity(), "Jumlah beli tidak kosong!", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(jumlah_beli.getText().toString()) == 0 ) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Anda yakin ingin hapus data?");

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //delete sparepart tersebut
                            Log.d("delete onClick", "pressed");

                            Intent intent = new Intent();

                            intent.putExtra("spare_pos", spare_pos);
                            intent.putExtra("jumlah_pengadaan", jumlah_beli.getText().toString());

                            try {
                                Log.d("onClick", "pressed");
                                getTargetFragment().onActivityResult(getTargetRequestCode(), 4, intent);
                                dismiss();
                            }catch (Exception e) {
                                Log.e("exception delete_btn", e.getMessage());
                                ((TampilPengadaan)getActivity())
                                        .deleteSparepart(Integer.parseInt(jumlah_beli.getText().toString()), Integer.parseInt(spare_pos));
                                dismiss();
                            }
                        }
                    });
                    builder.show();
                }
                else {
                    //update jumlah pengadaan
                    Log.d("save onClick", jumlah_beli.getText().toString() + " and " + spare_pos);
                    Intent intent = new Intent();

                    intent.putExtra("spare_pos", spare_pos);
                    intent.putExtra("jumlah_pengadaan", jumlah_beli.getText().toString());
                    try {
                        Log.d("onClick", "pressed");
                        getTargetFragment().onActivityResult(getTargetRequestCode(), 3, intent);
                        dismiss();
                    } catch (Exception e) {
                        Log.e("exception save_btn", e.getMessage());
                        ((TampilPengadaan) getActivity())
                                .updateSparepart(Integer.parseInt(jumlah_beli.getText().toString()), Integer.parseInt(spare_pos));
                        dismiss();
                    }
                }
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
