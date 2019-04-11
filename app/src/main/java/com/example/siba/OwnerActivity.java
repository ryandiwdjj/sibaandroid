package com.example.siba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class OwnerActivity extends AppCompatActivity {

    LinearLayout supplier_button;
    LinearLayout sparepart_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        Toolbar myToolBar = findViewById(R.id.owner_toolbar);
        myToolBar.setTitle("Owner");
        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(myToolBar);

        supplier_button = findViewById(R.id.supplier_btn);
        sparepart_button = findViewById(R.id.sparepart_btn);

        //SUPPLIER IMAGE BUTTON
        supplier_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent suplier_i = new Intent(OwnerActivity.this, OwnerSupplierActivity.class);
                startActivity(suplier_i);
            }
        });

        //SPAREPART IMAGE BUTTON
        sparepart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sparepart_i = new Intent(OwnerActivity.this, OwnerSparepartActivity.class);
                startActivity(sparepart_i);
            }
        });
    }
}
