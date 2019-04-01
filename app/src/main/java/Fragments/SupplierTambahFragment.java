package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siba.R;

import Models.supplier;
import Remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierTambahFragment extends Fragment implements View.OnClickListener {

    private EditText nama_supplier;
    private EditText sales_supplier;
    private EditText no_telp_supplier;
    private EditText alamat_supplier;
    private Button tambah_supplier_btn;

    UserService userService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_supplier_tambah, container, false);
        //userService = APIUtils.getUserService();
        nama_supplier = v.findViewById(R.id.nama_supplier_txt);
        sales_supplier = v.findViewById(R.id.sales_supplier_txt);
        no_telp_supplier = v.findViewById(R.id.no_telp_supplier_txt);
        alamat_supplier = v.findViewById(R.id.alamat_supplier_txt);
        tambah_supplier_btn = v.findViewById(R.id.tambah_supplier);

        tambah_supplier_btn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        if(nama_supplier.getText().toString().isEmpty() ||
                sales_supplier.getText().toString().isEmpty() ||
                no_telp_supplier.getText().toString().isEmpty() ||
                alamat_supplier.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Kotak harus terisi!", Toast.LENGTH_SHORT).show();
        }

        else {
            supplier s = new supplier();
            s.setNama_supplier(nama_supplier.getText().toString());
            s.setSales_supplier(sales_supplier.getText().toString());
            s.setNo_telp_supplier(no_telp_supplier.getText().toString());
            s.setAlamat_supplier(alamat_supplier.getText().toString());

            addSupplier(s);
        }
    }

    public void addSupplier(supplier s) {
        Call<supplier> call = userService.addSupplier(s);
        call.enqueue(new Callback<supplier>() {
            @Override
            public void onResponse(Call<supplier> call, Response<supplier> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "User created successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<supplier> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
