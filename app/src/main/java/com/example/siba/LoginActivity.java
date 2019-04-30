package com.example.siba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import API.ApiClient;
import API.ApiInterface;
import Models.pegawai;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Switch guest_switch;
    TextView password_txt;
    TextView phone_txt;
    Button login_btn;
    String login_cred;
    String login_role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        login_cred = sp.getString("login_cred", null);
        login_role = sp.getString("login_role", null);

        Log.d("sp login_cred", login_cred);
        Log.d("sp login_role", login_role);

        if(!login_cred.equals("null")) {
            //redirect
        }

        phone_txt = findViewById(R.id.phoneTxt);
        password_txt = findViewById(R.id.passwordTxt);
        guest_switch = findViewById(R.id.guestSwitch);
        login_btn = findViewById(R.id.loginBtn);

        Toolbar myToolBar = findViewById(R.id.owner_toolbar);
        myToolBar.setTitle("Login");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        guest_switch.setChecked(true);
        password_txt.setVisibility(View.INVISIBLE);
        guest_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guest_switch.isChecked()) {
                    //password_txt.setEnabled(false);
                    password_txt.setVisibility(View.INVISIBLE);
                    //Toast.makeText(LoginActivity.this, "ischecked", Toast.LENGTH_SHORT).show();
                } else {
                    //password_txt.setEnabled(true);
                    password_txt.setText("");
                    password_txt.setVisibility(View.VISIBLE);
                    //Toast.makeText(LoginActivity.this, "isNotChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login process
                if (guest_switch.isChecked()) { //masuk ke bagian pelanggan
                    if (phone_txt.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Nomor handphone harus terisi!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        //login pelanggan function
                    }
                }
                else { //bagian pegawai
                    if (phone_txt.getText().toString().isEmpty() ||
                            password_txt.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                        Call<pegawai> call = apiInterface.login(phone_txt.getText().toString(),
                                password_txt.getText().toString());

                        call.enqueue(new Callback<pegawai>() {
                            @Override
                            public void onResponse(Call<pegawai> call, Response<pegawai> response) {
                                if (response.isSuccessful()) {
                                    //save login credential
                                    SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                                    SharedPreferences.Editor ed = sp.edit();

                                    ed.putString("login_cred", phone_txt.getText().toString());
                                    ed.putString("login_role", response.body().getId_role().toString());
                                    ed.apply();

                                    login_cred = sp.getString("login_cred", null);

                                    if (response.body().getId_role() == 1) { // 1 == owner
                                        Log.d("login isSuccessfull", login_cred);
                                        //intent to another activity
                                        Intent i = new Intent(LoginActivity.this, OwnerActivity.class);
                                        startActivity(i);
                                    } else if (response.body().getId_role() == 2 || //2 customer service, 3 kasir, 4 montir
                                            response.body().getId_role() == 3 ||
                                            response.body().getId_role() == 4) {
                                        //intent menu pembayaran tok
                                        Toast.makeText(LoginActivity.this, "Pegawai", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Role tidak ada", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.e("onResponse", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<pegawai> call, Throwable t) {
                                Log.e("onFailure", t.getMessage());
                            }
                        });
                    }
                }
            }
        });

    }
}
