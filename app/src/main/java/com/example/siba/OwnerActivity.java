package com.example.siba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OwnerActivity extends AppCompatActivity {

    ImageButton supplier_button;
    ImageButton sparepart_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        supplier_button = findViewById(R.id.supplierBtn);
        sparepart_button = findViewById(R.id.sparepartBtn);

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
