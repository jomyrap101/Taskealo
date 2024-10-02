package com.example.taskealo_definitivo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleNota extends AppCompatActivity {

    private EditText txtTitulo, txtContenido;
    private Button btnModificar, btnEliminar, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_nota);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtContenido = findViewById(R.id.txtContenido);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnSalir = findViewById(R.id.btnSalir);

        // Obtener los extras que se pasaron
        final String titulo = getIntent().getStringExtra("titulo");
        final String contenido = getIntent().getStringExtra("contenido");

        // Configurar los TextViews
        txtTitulo.setText(titulo);
        txtContenido.setText(contenido);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("titulo", txtTitulo.getText().toString());
                resultIntent.putExtra("textoNota", txtContenido.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED); // No devolver nada si se elimina
                finish();
            }
        });
    }
}