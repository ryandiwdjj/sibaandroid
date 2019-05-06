package com.example.siba;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import API.ApiClient;
import API.ApiInterface;
//import BroadcastReceiver.SparepartCheck;
import Models.sparepart;
import Recycler.ClickListener;
import Recycler.RecyclerAdapterSparepartHargaJual;
//import androidx.work.Constraints;
//import androidx.work.OneTimeWorkRequest;
//import androidx.work.PeriodicWorkRequest;
//import androidx.work.WorkManager;
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
    private LinearLayout login_btn;
    private ImageView diag_image_view;

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    private String login_cred;
    private String login_role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //retrieve a PendingIntent that will perform a broadcast
//        Intent i = new Intent(this, SparepartCheck.class);
//        pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
//
//        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        int interval = 3000;
//
//        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

        try {
            SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
            login_cred = sp.getString("login_cred", null);
            login_role = sp.getString("login_role", null);

            Log.d("sp login_cred", login_cred);
            Log.d("sp login_role", login_role);
        }
        catch (Exception e) {
            SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("login_cred", "null");
            ed.putString("login_role", "null");
            ed.apply();

            login_cred = sp.getString("login_cred", null);
            login_role = sp.getString("login_role", null);


            Log.d("sp login_cred", login_cred);
            Log.d("sp login_role", login_role);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        SearchView search_txt = findViewById(R.id.search_box_txt);
        login_btn = findViewById(R.id.login_btn);
        diag_image_view = findViewById(R.id.dialog_image_view);



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
                Log.d("onQueryTextChange","triggered");

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
                spare = spareList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);

//                Picasso.get().load(spare.getGambar_sparepart()).into(diag_image_view);

                final View imageView = factory.inflate(R.layout.image_view, null);

                builder.setView(imageView);

                builder.show();
                Log.d("long click", spare.getId_sparepart().toString());
            }
        }));
        getSparepart();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getSparepart() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<List<sparepart>> call = apiInterface.getSparepart();
        call.enqueue(new Callback<List<sparepart>>() {
            @Override
            public void onResponse(Call<List<sparepart>> call, Response<List<sparepart>> response) {
                if(response.isSuccessful()) {
                    spareList = response.body();

                    progressDialog.dismiss();


                    recyclerAdapterSparepartHargaJual.notifyDataSetChanged();
                    recyclerAdapterSparepartHargaJual = new RecyclerAdapterSparepartHargaJual(getApplicationContext(), response.body()); //getresult()
                    recyclerView.setAdapter(recyclerAdapterSparepartHargaJual);
                }
                else {
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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_home) {
             Toast.makeText(this, "Home pressed", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_motorcycle) {
             Toast.makeText(this, "Motorcycle pressed", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_history) {
             Toast.makeText(this, "History pressed", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.log_in) {
             //Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
             Intent i = new Intent(MainActivity.this, LoginActivity.class);
             startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
