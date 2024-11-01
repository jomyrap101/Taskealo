package com.example.taskealo_definitivo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Crear_Nota extends AppCompatActivity {

    private EditText idTitulo, idTextoNota;
    private Button idCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);

        idTitulo = findViewById(R.id.idTitulo);
        idTextoNota = findViewById(R.id.idTextoNota);
        idCrear = findViewById(R.id.idCrear);

        // Llenar los campos si se está editando una nota
        if (getIntent().hasExtra("titulo") && getIntent().hasExtra("textoNota")) {
            idTitulo.setText(getIntent().getStringExtra("titulo"));
            idTextoNota.setText(getIntent().getStringExtra("textoNota"));
        }

        idCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = idTitulo.getText().toString();
                String texto = idTextoNota.getText().toString();

                if (titulo.isEmpty() || texto.isEmpty()) {
                    Toast.makeText(Crear_Nota.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("titulo", titulo);
                intent.putExtra("textoNota", texto);
                setResult(Activity.RESULT_OK, intent);
                finish(); // Cierra la actividad
            }
        });
    }
}
