package com.example.siba;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Switch guest_switch;
    TextView password_txt;
    TextView phone_txt;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone_txt = findViewById(R.id.phoneTxt);
        password_txt = findViewById(R.id.passwordTxt);
        guest_switch = findViewById(R.id.guestSwitch);
        login_btn = findViewById(R.id.loginBtn);

        guest_switch.setChecked(true);
        password_txt.setVisibility(View.INVISIBLE);
        guest_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(guest_switch.isChecked()) {
                    //password_txt.setEnabled(false);
                    password_txt.setVisibility(View.INVISIBLE);
                    //Toast.makeText(LoginActivity.this, "ischecked", Toast.LENGTH_SHORT).show();
                }
                else {
                    //password_txt.setEnabled(true);
                    password_txt.setVisibility(View.VISIBLE);
                    //Toast.makeText(LoginActivity.this, "isNotChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login process
                Intent i = new Intent(LoginActivity.this, OwnerActivity.class);
                startActivity(i);
                //save login credential
            }
        });
    }
}
