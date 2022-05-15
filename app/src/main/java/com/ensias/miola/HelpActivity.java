package com.ensias.miola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        btnBack=findViewById(R.id.Back_btn);
        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(HelpActivity.this,LoginActivity.class));  });
    }
}