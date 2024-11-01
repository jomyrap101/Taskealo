package com.example.taskealo_definitivo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Secretos extends AppCompatActivity {

    private Button salirPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretos);
        salirPag = (Button) findViewById(R.id.id_SalirSecreto);

        salirPag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresarPag = new Intent(Secretos.this, MainActivity.class);
                startActivity(regresarPag);
            }
        });

    }
}