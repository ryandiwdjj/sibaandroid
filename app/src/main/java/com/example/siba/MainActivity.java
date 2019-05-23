package com.example.siba;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
//import BroadcastReceiver.SparepartCheck;
import DialogFragment.HistoryDialogFragment;
import DialogFragment.PelangganDialogFragment;
import DialogFragment.StatusDialogFragment;
import Models.sparepart;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterSparepartHargaJual;
//import androidx.work.Constraints;
//import androidx.work.OneTimeWorkRequest;
//import androidx.work.PeriodicWorkRequest;
//import androidx.work.WorkManager;
import Service.SparepartCheck;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<sparepart> mListSparepart = new ArrayList<>();
    private List<sparepart> spareList;
    private RecyclerView recyclerView;
    public RecyclerAdapterSparepartHargaJual recyclerAdapterSparepartHargaJual;
    private RecyclerView.LayoutManager layoutManager;
    ApiInterface apiInterface;
    private sparepart spare;

    private SharedPreferences sp;

    private DrawerLayout drawer;

    private String login_cred;
    private String login_role;
    private String pelanggan_data;

    private View view;
    private TextView roles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        Log.d("date now", formattedDate);

        try {
            pelanggan_data = getIntent().getStringExtra("pelanggan_data");
        }
        catch (Exception e) {
            Log.e("exception", e.getMessage());
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        loginCheck();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        SearchView search_txt = findViewById(R.id.search_box_txt);


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
                recyclerAdapterSparepartHargaJual.getSearchFilter().filter(newText);
                Log.d("onQueryTextChange", "triggered");

                return false;
            }
        });

        recyclerView = findViewById(R.id.recycler_view_sparepart);
        recyclerAdapterSparepartHargaJual = new RecyclerAdapterSparepartHargaJual(MainActivity.this, mListSparepart);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterSparepartHargaJual);

        recyclerView.addOnItemTouchListener(new RecyclerAdapterSparepartHargaJual(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                spare = spareList.get(position);
                //mindah data sup pake json
                Intent intent = new Intent(MainActivity.this, TampilSparepartNonEdit.class);
                intent.putExtra("sparepart_object", new Gson().toJson(spare));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
//                spare = spareList.get(position);
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
//
//                Picasso.get().load(spare.getGambar_sparepart()).into(diag_image_view);
//
//                final View imageView = factory.inflate(R.layout.image_view, null);
//
//                builder.setView(imageView);
//
//                builder.show();
//                Log.d("long click", spare.getId_sparepart().toString());
            }
        }));
        getSparepart();


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        LinearLayout profile = view.findViewById(R.id.profile_btn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                login_cred = sp.getString("login_cred", null);
                login_role = sp.getString("login_role", null);

                Log.d("login_cred", login_cred);
                Log.d("login_role", login_role);

                if (login_cred.equals("null") && login_role.equals("null")) {

                    drawer.closeDrawers();
                    Toast.makeText(MainActivity.this, "Anda Belum Login!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (login_role.equals("1")) { // 1 == owner
                        //intent to another activity
                        Intent i = new Intent(MainActivity.this, OwnerActivity.class);
                        startActivity(i);
                    }
                    else if (login_role.equals("2") || //2 customer service, 3 kasir, 4 montir
                            login_role.equals("3") ||
                            login_role.equals("4")) {
                        //intent menu pembayaran tok
                        Intent i = new Intent(MainActivity.this, PenjualanActivity.class);
                        startActivity(i);
                    }
                    else if (login_role.equals("g")) { // 1 == owner
                        //intent to another activity
                        drawer.closeDrawers();

                        PelangganDialogFragment dialog = new PelangganDialogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", login_cred);

                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "dialog");
                    }
                    else {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawers();
                        Toast.makeText(MainActivity.this, "Anda Belum Login!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void loginCheck() {
        try {
            sp = getSharedPreferences("login", MODE_PRIVATE);
            login_cred = sp.getString("login_cred", null);
            login_role = sp.getString("login_role", null);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            view = navigationView.getHeaderView(0);
            roles = view.findViewById(R.id.login_role);

            if (login_role.equals("1")) { // 1 == owner
                //intent to another activity
                roles.setText("Halo, Owner!");
            }
            else if (login_role.equals("2") || //2 customer service, 3 kasir, 4 montir
                    login_role.equals("3") ||
                    login_role.equals("4")) {
                roles.setText("Halo, Pegawai!");
            }

            else if(login_role.equals("g")) {
                roles.setText("Halo, Tamu!");
            }

            Log.d("login_cred", login_cred);
            Log.d("login_role", login_role);
        }
        catch (Exception e) {
            sp = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("login_cred", "null");
            ed.putString("login_role", "null");
            ed.apply();

            login_cred = sp.getString("login_cred", null);
            login_role = sp.getString("login_role", null);

            roles.setText("Atma Auto");

            Log.d("sp login_cred created", login_cred);
            Log.d("sp login_role created", login_role);
        }
    }

    private void getSparepart() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<List<sparepart>> call = apiInterface.getSparepart();
        call.enqueue(new Callback<List<sparepart>>() {
            @Override
            public void onResponse(Call<List<sparepart>> call, Response<List<sparepart>> response) {
                if (response.isSuccessful()) {
                    spareList = response.body();

                    progressDialog.dismiss();

                    recyclerAdapterSparepartHargaJual.notifyDataSetChanged();
                    recyclerAdapterSparepartHargaJual = new RecyclerAdapterSparepartHargaJual(getApplicationContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterSparepartHargaJual);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        getSparepart();
        loginCheck();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Anda yakin ingin keluar?");

            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
//                    MainActivity.super.onBackPressed();
                }
            });
            builder.show();

//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) { //home
//            Toast.makeText(this, "Home pressed", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_motorcycle) { //status reparasi
//            Toast.makeText(this, "Motorcycle pressed", Toast.LENGTH_SHORT).show();
            if (login_role.equals("g")) { // 1 == owner
                //intent to another activity
                drawer.closeDrawers();

                StatusDialogFragment dialog = new StatusDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("login_cred", login_cred);

                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
            else {
                Toast.makeText(this, "Tidak ada", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_history) { //history pelanggan
//            Toast.makeText(this, "History pressed", Toast.LENGTH_SHORT).show();
            if (login_role.equals("g")) { // 1 == owner
                //intent to another activity
                drawer.closeDrawers();

                HistoryDialogFragment dialog = new HistoryDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("login_cred", login_cred);

                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
            else {
                Toast.makeText(this, "Tidak ada", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.log_in) {
            //Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
            SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
            login_cred = sp.getString("login_cred", null);
            login_role = sp.getString("login_role", null);

            if (login_cred.equals("null") && login_role.equals("null")) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Anda sudah Login!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.log_out) {
            sp = getSharedPreferences("login", MODE_PRIVATE);
            login_cred = sp.getString("login_cred", null);
            login_role = sp.getString("login_role", null);

            if (login_cred.equals("null") && login_role.equals("null")) {
                Log.d("login", "sudah");
                Toast.makeText(this, "Anda Belum Login!", Toast.LENGTH_SHORT).show();
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Anda yakin ingin logout?");

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //log out function
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("login_cred", "null");
                        ed.putString("login_role", "null");
                        ed.apply();

                        roles.setText("Atma Auto");

                        Toast.makeText(MainActivity.this, "Anda Berhasil Logout!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
